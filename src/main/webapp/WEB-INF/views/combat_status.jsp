<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="resources/style.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>${title}</title>
</head>
<body>
	<h1>${player.getName()} - ${player.getShip().getName()}</h1>
	<c:if test="${not empty waitingOnPlayers}">
	<i>Waiting On:<c:forEach var="waitPlayer" items="${waitingOnPlayers}"> - ${waitPlayer.getName()}</c:forEach> -</i>
	</c:if>
	<c:if test="${empty waitingOnPlayers}">
	Proceed!
	</c:if>
	<div class="console">
	<form id="selectShip" method="POST">
	<input name="action" type="hidden" value="selectShip" />
	<input name="selectedShip" type="hidden" />
	</form>
	<form id="selectComponent" method="POST">
	<input name="action" type="hidden" value="selectComponent" />
	<input name="selectedComponent" type="hidden" />
	</form>
	<c:if test="${! player.isFinalized()}">
	<form id="finalizePlayerIntents" method="POST">
		<input name="action" type="hidden" value="finalizePlayerIntents" />
		<input type="submit" value="I'm ready to proceed"></input>
		</form>
	</c:if>
	<table class="combatStatus">
		<tbody>
			<tr>
				<th style="text-align: center; width: 9%;">Beyond Extreme</th>
				<th style="text-align: center; width: 9%;">Extreme</th>
				<th style="text-align: center; width: 9%;">Long</th>
				<th style="text-align: center; width: 9%;">Medium</th>
				<th style="text-align: center; width: 9%;">Short</th>
				<th style="text-align: center;">&lt;&lt;${player.getShip().getName()}&lt;&lt;</th>
				<th style="text-align: center; width: 9%;">Short</th>
				<th style="text-align: center; width: 9%;">Medium</th>
				<th style="text-align: center; width: 9%;">Long</th>
				<th style="text-align: center; width: 9%;">Extreme</th>
				<th style="text-align: center; width: 9%;">Beyond Extreme</th>
			</tr>
			<c:forEach var="detectedShip" items="${ship.getDetectedShips().values()}">
			<tr>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 0}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 1}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 2}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 3}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 4}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 5}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 6}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 7}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 8}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 9}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
				<td style="text-align: center; width: 9%;">
				<c:if test="${detectedShip.getColumnForDisplay(ship) == 10}">
				<a href="#" target="_self" onClick="document.forms['selectShip'].selectedShip.value='${detectedShip.getId()}';document.forms['selectShip'].submit();">${detectedShip.getDisplayNameWithFacing(ship)}</a>
				</c:if>
				</td>
			</tr>
			</c:forEach>
			
		</tbody>
	</table>
	</div>
	<table class="combatStatus">
		<tbody>
			<tr style="overflow-x: auto;">
				<td style="text-align: center;">Visual Ship Status</td>
			</tr>
			<tr style="overflow-x: auto;">
				<td>
					<table
						style="width: 100%; height: 100%; margin-left: auto; margin-right: auto;"
						border="5">
						<tbody>
							<tr>
								<td>
									<c:choose>
										<c:when test="${not empty ship}">
											<b>${ship.getName()}</b> <b>HP:</b>${ship.getCurrHP()}/${ship.getMaxHP()} <b>Shields:</b>${ship.getCurrShields()}/${ship.getMaxShields()} <b>Armor:</b>${ship.getCurrArmor()}/${ship.getMaxArmor()} <b>TL:</b>${ship.getTargetLock()}<br/>
											T${ship.getTier().getDescription()} ${ship.getFrame().getSize().getDescription()} ${ship.getFrame().getDescription()} (Maneuverability ${ship.getFrame().getManeuverability().getDescription()})<br />
											<c:if test="${not empty ship.getAIModuleComponents()}">
												<b>AI Modules: </b>
												<c:forEach var="component" items="${ship.getAIModuleComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getArmorComponents()}">
												<b>Armor: </b>
												<c:forEach var="component" items="${ship.getArmorComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getCommGridComponents()}">
												<b>Comm Grids: </b>
												<c:forEach var="component" items="${ship.getCommGridComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getCommunicationsComponents()}">
												<b>Communications: </b>
												<c:forEach var="component" items="${ship.getCommunicationsComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getEngineComponents()}">
												<b>Engines: </b>
												<c:forEach var="component" items="${ship.getEngineComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getFlakComponents()}">
												<b>Flak Batteries: </b>
												<c:forEach var="component" items="${ship.getFlakComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getPassiveSensorsComponents()}">
												<b>Passive Sensors: </b>
												<c:forEach var="component" items="${ship.getPassiveSensorsComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getPowerCoreComponents()}">
												<b>Power Cores: </b>
												<c:forEach var="component" items="${ship.getPowerCoreComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												&nbsp;Net Energy: ${ship.getTotalPowerUsed()}/${ship.getTotalPowerAvailable()} = ${ship.getNetPowerAvailable()}
												<br/>
											</c:if>
											<c:if test="${not empty ship.getSecurityModuleComponents()}">
												<b>Security Modules: </b>
												<c:forEach var="component" items="${ship.getSecurityModuleComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getSensorArrayComponents()}">
												<b>Sensor Arrays: </b>
												<c:forEach var="component" items="${ship.getSensorArrayComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getShieldGeneratorComponents()}">
												<b>Shield Generators: </b>
												<c:forEach var="component" items="${ship.getShieldGeneratorComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getStealthFieldComponents()}">
												<b>Stealth Field Generators: </b>
												<c:forEach var="component" items="${ship.getStealthFieldComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getThrustersComponents()}">
												<b>Thrusters: </b>
												<c:forEach var="component" items="${ship.getThrustersComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getTractorBeamComponents()}">
												<b>Tractor Beams: </b>
												<c:forEach var="component" items="${ship.getTractorBeamComponents()}">
													<a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${component.getId()}';document.forms['selectComponent'].submit();">${component.getBadgeFor(ship)}</a>
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty ship.getWeapons()}">
												<c:forEach var="weapon" items="${ship.getWeapons()}">
													<b><a href="#" target="_self" onClick="document.forms['selectComponent'].selectedComponent.value='${weapon.getId()}';document.forms['selectComponent'].submit();">${weapon.getName()}</a></b> ${weapon.getBadgeFor(ship)}<br/>
												</c:forEach>
												<br/>
											</c:if>
										</c:when>
										<c:otherwise>
											You appear to be floating in space. Where is your ship???
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${not empty selectedShip}">
											<b>${selectedShip.getName()}</b> <b>HP:</b>${selectedShip.getCurrHP()}/${selectedShip.getMaxHP()} <b>Shields:</b>${selectedShip.getCurrShields()}/${selectedShip.getMaxShields()} <b>Armor:</b>${selectedShip.getCurrArmor()}/${selectedShip.getMaxArmor()} <b>TL:</b>${selectedShip.getTargetLock()}<br/>
											T${selectedShip.getTier().getDescription()} ${selectedShip.getFrame().getSize().getDescription()} ${selectedShip.getFrame().getDescription()} (Maneuverability ${selectedShip.getFrame().getManeuverability().getDescription()})<br />
											<c:if test="${not empty selectedShip.getAIModuleComponents()}">
												<b>AI Modules: </b>
												<c:forEach var="component" items="${selectedShip.getAIModuleComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getCommGridComponents()}">
												<b>Comm Grids: </b>
												<c:forEach var="component" items="${selectedShip.getCommGridComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getCommunicationsComponents()}">
												<b>Communications: </b>
												<c:forEach var="component" items="${selectedShip.getCommunicationsComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getEngineComponents()}">
												<b>Engines: </b>
												<c:forEach var="component" items="${selectedShip.getEngineComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getFlakComponents()}">
												<b>Flak Batteries: </b>
												<c:forEach var="component" items="${selectedShip.getFlakComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getPassiveSensorsComponents()}">
												<b>Passive Sensors: </b>
												<c:forEach var="component" items="${selectedShip.getPassiveSensorsComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getPowerCoreComponents()}">
												<b>Power Cores: </b>
												<c:forEach var="component" items="${selectedShip.getPowerCoreComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getSecurityModuleComponents()}">
												<b>Security Modules: </b>
												<c:forEach var="component" items="${selectedShip.getSecurityModuleComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getSensorArrayComponents()}">
												<b>Sensor Arrays: </b>
												<c:forEach var="component" items="${selectedShip.getSensorArrayComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getShieldGeneratorComponents()}">
												<b>Shield Generators: </b>
												<c:forEach var="component" items="${selectedShip.getShieldGeneratorComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getStealthFieldComponents()}">
												<b>Stealth Field Generators: </b>
												<c:forEach var="component" items="${selectedShip.getStealthFieldComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getThrustersComponents()}">
												<b>Thrusters: </b>
												<c:forEach var="component" items="${selectedShip.getThrustersComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getTractorBeamComponents()}">
												<b>Tractor Beams: </b>
												<c:forEach var="component" items="${selectedShip.getTractorBeamComponents()}">
													${component.getBadgeFor(selectedShip)}
												</c:forEach>
												<br/>
											</c:if>
											<c:if test="${not empty selectedShip.getWeapons()}">
												<c:forEach var="weapon" items="${selectedShip.getWeapons()}">
													<b>${weapon.getName()}</b> ${component.getBadgeFor(selectedShip)}:<br/>
												</c:forEach>
												<br/>
											</c:if>
										</c:when>
										<c:otherwise>
											No target ship chosen.
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr style="overflow-x: auto;">
								<td>
									<c:choose>
										<c:when test="${not empty player.getCurrentConsole()}">
										<b>${player.getCurrentConsole().getName()}</b> ${player.getCurrentConsole().getConsoleStatus()}<br/>
										<c:forEach var="intent" items="${player.getCurrentConsole().getPossibleIntents(selectedShip)}">
										${intent}<br/>
										</c:forEach>
										</c:when>
										<c:otherwise>
										You do not have a console under your control.
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:if test="${player.getShip().getPilot().equals(player)}">
									<b>Pilot: </b><br/>
										<c:if test="${not empty selectedShip}">
										<form id="selectPreferredDistance" method="POST">
											<input name="action" type="hidden" value="selectPreferredDistance" />
											<input name="selectedShip" type="hidden" value="${selectedShip}" />
										Preferred Distance from ${selectedShip.getName()} <select name="preferredDistance" onchange="submit();">
							         	<option value="POINT_BLANK" ${"POINT_BLANK" == player.ship.getPreferredDistanceFrom(selectedShip) ? 'selected' : ''}>Point Blank</option>
							         	<option value="SHORT" ${"SHORT" == player.ship.getPreferredDistanceFrom(selectedShip) ? 'selected' : ''}>Short</option>
							         	<option value="MEDIUM" ${"MEDIUM" == player.ship.getPreferredDistanceFrom(selectedShip) ? 'selected' : ''}>Medium</option>
							         	<option value="LONG" ${"LONG" == player.ship.getPreferredDistanceFrom(selectedShip) ? 'selected' : ''}>Long</option>
							         	<option value="EXTREME" ${"EXTREME" == player.ship.getPreferredDistanceFrom(selectedShip) ? 'selected' : ''}>Extreme</option>
							         	<option value="BEYOND_EXTREME" ${"BEYOND_EXTREME" == player.ship.getPreferredDistanceFrom(selectedShip) ? 'selected' : ''}>Beyond Extreme</option>
								         </select>
							         </form>
										<br/>
										</c:if>
									<br/>
									</c:if>
									<c:choose>
										<c:when test="${not empty player}">
										<b>Crewmember actions for ${player.getName()}:</b><br/>
										<c:forEach var="intent" items="${player.getPossibleIntents()}">
										${intent}<br/>
										</c:forEach>
										</c:when>
										<c:otherwise>
										Who are you even? It's like I don't even know you any more.
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
