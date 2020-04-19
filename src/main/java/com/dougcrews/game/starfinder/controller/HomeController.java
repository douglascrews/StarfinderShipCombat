package com.dougcrews.game.starfinder.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

@Controller
public class HomeController {
	private static final Logger log = LogManager.getLogger(HomeController.class.getName());

	private CombatController cc;

	@SuppressWarnings("unused")
	@RequestMapping(value="/")
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	{
		String action = request.getParameter("action");
		ModelAndView mav;
		switch("" + action)
		{
		default:
			mav = getLoginMAV(session, null);
			break;
		}
		showSession(session);
		return mav;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value="/", method=RequestMethod.POST)
	public ModelAndView controller(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	{
		log.trace("controller()");
		showSession(session);
		String action = request.getParameter("action");
		ModelAndView mav;
		switch("" + action)
		{
		case "login":
			if ("".equals(session.getAttribute("playerName")))
			{
				mav = this.getLoginMAV(session, null);
			}
			else
			{
				this.setPlayer(session, request);
				mav = this.getCombatStatusMAV(session);
			}
			break;
		case "createPlayer":
			this.createPlayer(session, request);
			mav = this.getCombatStatusMAV(session);
			break;
		case "selectShip":
			setSelectedShip(session, request);
			mav = this.getCombatStatusMAV(session);
			break;
		case "selectComponent":
			setSelectedComponent(session, request);
			mav = this.getCombatStatusMAV(session);
			break;
		case "selectPreferredDistance":
			setPreferredDistance(session, request);
			mav = this.getCombatStatusMAV(session);
			break;
		case "finalizePlayerIntents":
			finalizePlayerIntents(session, request);
			mav = this.getCombatStatusMAV(session);
			break;
		case "startCombatRound":
			this.startCombatRound(session, request);
			mav = this.getCombatStatusMAV(session);
			break;
		default:
			mav = this.getCombatStatusMAV(session);
			break;
		}
		showSession(session);
		return mav;
	}

	private void finalizePlayerIntents(HttpSession session, @SuppressWarnings("unused") HttpServletRequest request)
	{
		Player player = (Player) session.getAttribute("player");
		if (player != null)
		{
			this.cc.finalizePlayerIntents(player);
		}
	}

	private void startCombatRound(HttpSession session, @SuppressWarnings("unused") HttpServletRequest request)
	{
		this.cc.startCombatRound();
		session.setAttribute("initiatives", this.cc.getShipInitiatives());
	}

	private static void showSession(HttpSession session)
	{
		log.debug("Session attributes:");
		@SuppressWarnings("unchecked")
		Enumeration<String> e = session.getAttributeNames();
		while (e.hasMoreElements())
		{
			String attr = e.nextElement();
			log.debug(attr + "=" + session.getAttribute(attr));
		}
	}

	private void setPlayer(HttpSession session, HttpServletRequest request)
	{
		String playerName = request.getParameter("playerName");
		session.setAttribute("playerName", playerName);
		Player player = null;
		if (playerName != null)
		{
			player = this.cc.getPlayer(playerName);
		}
		if (player != null)
		{
			session.setAttribute("player", player);
			session.setAttribute("ship", player.getShip());
		}
	}
	
	private void createPlayer(HttpSession session, HttpServletRequest request)
	{
		String playerName = request.getParameter("playerName");
		String shipName = request.getParameter("shipName");
		int computersBonus = Integer.valueOf(request.getParameter("computersBonus"));
		int diplomacyBonus = Integer.valueOf(request.getParameter("diplomacyBonus"));
		int engineeringBonus = Integer.valueOf(request.getParameter("engineeringBonus"));
		int gunneryBonus = Integer.valueOf(request.getParameter("gunneryBonus"));
		int intimidateBonus = Integer.valueOf(request.getParameter("intimidateBonus"));
		int pilotBonus = Integer.valueOf(request.getParameter("pilotBonus"));
		Ship ship = null;
		for (Ship s : this.cc.getShips())
		{
			if (s.getName().equals(shipName)) ship = s;
		}
		Player player = new Player(playerName, ship, pilotBonus, gunneryBonus, engineeringBonus, computersBonus, diplomacyBonus, intimidateBonus);
		this.cc.addPlayer(player);
		session.setAttribute("player", player);
		session.setAttribute("ship", ship);
	}

	private void setSelectedShip(HttpSession session, HttpServletRequest request)
	{
		String selectedShipName = request.getParameter("selectedShip");
		Ship ship = (Ship) session.getAttribute("ship");
		Ship selectedShip = null;
		if (selectedShipName != null)
		{
			selectedShip = this.cc.getShip(selectedShipName);
		}
		DetectedShip detectedShip = null;
		if (selectedShip != null)
		{
			if (ship != null)
			{
				detectedShip = ship.getDetectedShip(selectedShip);
			}
			session.setAttribute("selectedShip", detectedShip);
		}
	}

	private static void setSelectedComponent(HttpSession session, HttpServletRequest request)
	{
		String selectedComponentId = request.getParameter("selectedComponent");
		Ship ship = (Ship) session.getAttribute("ship");
		ShipComponent selectedComponent = null;
		if (selectedComponentId != null)
		{
			selectedComponent = ship.getComponent(selectedComponentId);
		}
		Player player = (Player) session.getAttribute("player");
		if (player != null)
		{
			player.setCurrentConsole(selectedComponent);
		}
	}
	
	private static void setPreferredDistance(HttpSession session, HttpServletRequest request)
	{
		String preferredDistance = request.getParameter("preferredDistance");
		Distance distance = Distance.POINT_BLANK; // TODO: inelegant to have a default like this
		if ("POINT_BLANK".equals(preferredDistance)) distance = Distance.POINT_BLANK;
		if ("SHORT".equals(preferredDistance)) distance = Distance.SHORT;
		if ("MEDIUM".equals(preferredDistance)) distance = Distance.MEDIUM;
		if ("LONG".equals(preferredDistance)) distance = Distance.LONG;
		if ("EXTREME".equals(preferredDistance)) distance = Distance.EXTREME;
		if ("BEYOND_EXTREME".equals(preferredDistance)) distance = Distance.BEYOND_EXTREME;
		Ship ship = (Ship) session.getAttribute("ship");
		DetectedShip selectedShip = (DetectedShip) session.getAttribute("selectedShip");
		if (selectedShip != null)
		{
			Ship otherShip = selectedShip.getActualShip();
			ship.setPreferredDistanceFrom(otherShip, distance);
		}
	}
	
	public ModelAndView getLoginMAV(HttpSession session, Player player)
	{
		ModelAndView mav = new ModelAndView("login");
		session.setAttribute("title", "Ship Combat Simulator");
		session.setAttribute("player", player);
		if (this.cc == null || this.cc.getShips().isEmpty())
		{
			this.setupCombat(); // TODO: temporary, testing only
		}
		session.setAttribute("players", this.cc.getPlayers());
		session.setAttribute("ships", this.cc.getShips());
		return mav;
	}

	public ModelAndView getCombatStatusMAV(HttpSession session)
	{
		log.trace("getCombatStatusMAV(session)");
		showSession(session);
		ModelAndView mav = new ModelAndView("combat_status");
		session.setAttribute("title", "Ship Combat Sim");
		session.setAttribute("ships", this.cc.getShips());
		Player player = (Player) session.getAttribute("player");
		if (player != null)
		{
			session.setAttribute("player", this.cc.getPlayer(player.getName()));
		}
		DetectedShip selectedShip = (DetectedShip) session.getAttribute("selectedShip");
		if (selectedShip != null && player != null)
		{
			session.setAttribute("selectedShip", player.getShip().getDetectedShip(selectedShip.getActualShip()));
		}
		session.setAttribute("waitingOnPlayers", this.cc.getWaitingOnList());
		return mav;
	}
		
	public void setupCombat()
	{
		this.cc = new CombatController();
		this.cc.generateTestArena();
	}
	
	public HomeController()
	{
		this.cc = new CombatController();
		this.cc.generateTestArena(); // TODO: temporary
	}
}

