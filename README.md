# Starfinder Ship Combat

## Description

A better Homebrew ruleset for starship combat rules in the [Starfinder role playing game](https://paizo.com/starfinder/).
- Starship combat happens on the same time scale as melee combat, so combatants may have to choose between firing missiles or repelling a hostile boarding party.
- Starships are built from standard components, which generally provide more capability and range with additional similar components.
- For example, a single Communications Array component provides +1 to dice rolls, and capability out to Short Range. Adding a second provides +2 to rolls, and extends range out to Medium.
- Targeting of individual components is a valid tactic, to blind your enemy or take away their weapons or even Life Support.
- Shields and Armor provide distinct benefits against different types of weapons, and different types of weapons are less or more effective against Shields, Armor, and Hull.
- Most actions have one or more Reactions that the opposing ship can take. Being targeted by an attempt to inject a virus into your computer? Turn it back on them, and let the best hacker win!
- More fun options for Pilots and Captains than the standard rules allow.
- Simplified spatial rules which only involve distance between ships and the intents of the pilots (i.e., get closer, run away, keep current distance).

This is a work in progress. Not all rules have been implemented, but what is there is generally functional.

## Table of Contents (Optional)

- [Installation](#installation)
- [Usage](#usage)
- [License](#license)
- [Tests](#tests)

## Installation

Standard Maven installation.

## Usage

The JUnit tests in /src/test/java are probably the best indicator how how the system is supposed to work.

See /src/test/java/com/dougcrews/game/starfinder/controller/CombatControllerTest.java for simple two-ship combat.
See /src/test/java/com/dougcrews/game/starfinder/model/ship/ShipTest.java for basic building ship rules.

## License

[GNU GPLv3](LICENSE.txt) (do what you like with it, but please don't distribute closed source versions without permission).

## Tests

```sh
$ mvn test
```

```
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------< com.dougcrews.game:starfinder-ship-combat >--------------
[INFO] Building starfinder-ship-combat 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.0:resources (default-resources) @ starfinder-ship-combat ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 1 resource
[INFO] 
[INFO] --- compiler:2.0.2:compile (default-compile) @ starfinder-ship-combat ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- resources:3.3.0:testResources (default-testResources) @ starfinder-ship-combat ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory C:\Users\dcrews\Projects\StarfinderShipCombat\src\test\resources
[INFO] 
[INFO] --- compiler:2.0.2:testCompile (default-testCompile) @ starfinder-ship-combat ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- surefire:3.0.0:test (default-test) @ starfinder-ship-combat ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.dougcrews.game.starfinder.controller.CombatControllerTest
2023-04-10 16:42:10 INFO  CombatController:245 - Combat starting
2023-04-10 16:42:10 INFO  CombatController:248 - Ship Status:
Enterprise (HP:75 Shields:10 Armor:2)
Captain: Enterprise GEE-M
Pilot: Enterprise GEE-M
Components:
A.I. Module: [ ][ ][ ][ ][ ]
Armor: [ ][ ]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ][ ][ ]
Shield Generator: [ ][ ]

2023-04-10 16:42:10 INFO  CombatController:248 - Ship Status:
Klingon Battle Cruiser (HP:55 Shields:10 Armor:1)
Captain: Klingon Battle Cruiser GEE-M
Pilot: Commander T'Putz
Components:
Armor: [ ]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ]
Shield Generator: [ ][ ]

2023-04-10 16:42:10 INFO  CombatController:270 - ********** Starting Round 1
2023-04-10 16:42:10 DEBUG Ship:682 - Enterprise detects Klingon Battle Cruiser
2023-04-10 16:42:10 DEBUG Ship:682 - Klingon Battle Cruiser detects Enterprise
2023-04-10 16:42:10 INFO  Ship:585 - Initiative roll Enterprise: (16) + 2 = 18
2023-04-10 16:42:10 INFO  Ship:585 - Initiative roll Klingon Battle Cruiser: (11) + -2 = 9
2023-04-10 16:42:10 DEBUG CombatController:405 - Status: ShipToShipRelation[Enterprise]....[Klingon Battle Cruiser]
2023-04-10 16:42:10 DEBUG Ship:784 - Comparing Klingon Battle Cruiser to Enterprise
2023-04-10 16:42:10 INFO  CombatController:467 - Enterprise (HP:75 Shields:10 Armor:2)
2023-04-10 16:42:10 DEBUG Dice:22 - coin flip = heads
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Missile Launcher at Klingon Battle Cruiser: (12) + 20 + 0 + 0 + -6 = 26 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Energy Weapon at Klingon Battle Cruiser: (18) + 20 + 0 + 0 + -6 = 32 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 1
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 1
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 1 damage
2023-04-10 16:42:10 INFO  Ship:104 - Shield Generator 1 damaged for 1 points!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Kinetic Weapon at Klingon Battle Cruiser: (20) + 20 + 0 + 0 + -6 = 34 vs. TL9 -> Hit!
2023-04-10 16:42:10 INFO  Weapon:86 - Critical Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 5
2023-04-10 16:42:10 DEBUG Weapon:132 - crit damage = 4
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 9
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 9 damage
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 INFO  CombatController:467 - Klingon Battle Cruiser (HP:55 Shields:9 Armor:1)
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«MÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Move action.
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«SÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Standard action.
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«RÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Reaction action.
2023-04-10 16:42:10 DEBUG Dice:22 - coin flip = heads
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Missile Launcher at Enterprise: (14) + 1 + 0 + 0 + -6 = 9 vs. TL8 -> Hit!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Energy Weapon at Enterprise: (19) + 1 + 0 + 0 + -6 = 14 vs. TL8 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 5
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 5
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 5 damage
2023-04-10 16:42:10 INFO  Ship:104 - Shield Generator 1 damaged for 5 points!
2023-04-10 16:42:10 INFO  Ship:107 - Shield Generator 1 is disabled by battle damage!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Kinetic Weapon at Enterprise: (9) + 1 + 0 + 0 + -6 = 4 vs. TL8 -> Miss
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Enterprise)
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Klingon Battle Cruiser)
2023-04-10 16:42:10 INFO  CombatController:529 - ********** End of Round 1
2023-04-10 16:42:10 INFO  CombatController:532 - Ship Status:
Enterprise (HP:75 Shields:5 Armor:2)
Captain: Enterprise GEE-M
Pilot: Enterprise GEE-M
Components:
A.I. Module: [ ][ ][ ][ ][ ]
Armor: [ ][ ]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ][ ][ ]
Shield Generator: [X][ ]

2023-04-10 16:42:10 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:10 INFO  CombatController:536 - Small Seeker Missile from Enterprise targeting Klingon Battle Cruiser at Long range and closing
2023-04-10 16:42:10 INFO  CombatController:532 - Ship Status:
Klingon Battle Cruiser (HP:55 Shields:9 Armor:1)
Captain: Klingon Battle Cruiser GEE-M
Pilot: Commander T'Putz
Components:
Armor: [ ]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ]
Shield Generator: [4][ ]

2023-04-10 16:42:10 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:10 INFO  CombatController:536 - Small Seeker Missile from Klingon Battle Cruiser targeting Enterprise at Long range and closing
2023-04-10 16:42:10 INFO  CombatController:270 - ********** Starting Round 2
2023-04-10 16:42:10 INFO  Ship:585 - Initiative roll Enterprise: (6) + 2 = 8
2023-04-10 16:42:10 INFO  Ship:585 - Initiative roll Klingon Battle Cruiser: (16) + -2 = 14
2023-04-10 16:42:10 DEBUG CombatController:405 - Status: ShipToShipRelation[Enterprise]...[Klingon Battle Cruiser]
2023-04-10 16:42:10 DEBUG Ship:784 - Comparing Klingon Battle Cruiser to Enterprise
2023-04-10 16:42:10 INFO  CombatController:467 - Klingon Battle Cruiser (HP:55 Shields:9 Armor:1)
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«MÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Move action.
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«SÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Standard action.
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«RÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Reaction action.
2023-04-10 16:42:10 DEBUG Dice:22 - coin flip = tails
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Missile Launcher at Enterprise: (8) + 1 + 0 + 0 + -4 = 5 vs. TL8 -> Miss
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Energy Weapon at Enterprise: (16) + 1 + 0 + 0 + -4 = 13 vs. TL8 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 6
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 6
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 5 damage
2023-04-10 16:42:10 INFO  Ship:104 - Shield Generator 2 damaged for 5 points!
2023-04-10 16:42:10 INFO  Ship:107 - Shield Generator 2 is disabled by battle damage!
2023-04-10 16:42:10 INFO  Ship:134 - Armor absorbs 1 damage
2023-04-10 16:42:10 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Kinetic Weapon at Enterprise: (15) + 1 + 0 + 0 + -4 = 12 vs. TL8 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 4
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 4
2023-04-10 16:42:10 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 INFO  Ship:134 - Armor absorbs 2 damage
2023-04-10 16:42:10 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:10 INFO  Ship:183 - Hull absorbs 2 damage
2023-04-10 16:42:10 INFO  Ship:193 - Enterprise hull hit for 2 damage!
2023-04-10 16:42:10 INFO  CombatController:467 - Enterprise (HP:73 Shields:0 Armor:2)
2023-04-10 16:42:10 DEBUG Dice:22 - coin flip = tails
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Missile Launcher at Klingon Battle Cruiser: (18) + 20 + 0 + 0 + -4 = 34 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Energy Weapon at Klingon Battle Cruiser: (5) + 20 + 0 + 0 + -4 = 21 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 4
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 4
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 4 damage
2023-04-10 16:42:10 INFO  Ship:104 - Shield Generator 2 damaged for 4 points!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Kinetic Weapon at Klingon Battle Cruiser: (10) + 20 + 0 + 0 + -4 = 26 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 1
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 1
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 1 damage
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Enterprise)
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Guided Missile (Enterprise)
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Klingon Battle Cruiser)
2023-04-10 16:42:10 INFO  CombatController:529 - ********** End of Round 2
2023-04-10 16:42:10 INFO  CombatController:532 - Ship Status:
Enterprise (HP:73 Shields:0 Armor:2)
Captain: Enterprise GEE-M
Pilot: Enterprise GEE-M
Components:
A.I. Module: [ ][ ][ ][ ][ ]
Armor: [ ][ ]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ][ ][ ]
Shield Generator: [X][X]

2023-04-10 16:42:10 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:10 INFO  CombatController:536 - Small Seeker Missile from Enterprise targeting Klingon Battle Cruiser at Medium range and closing
2023-04-10 16:42:10 INFO  CombatController:536 - Small Guided Missile from Enterprise targeting Klingon Battle Cruiser at Medium range and closing
2023-04-10 16:42:10 INFO  CombatController:532 - Ship Status:
Klingon Battle Cruiser (HP:55 Shields:5 Armor:1)
Captain: Klingon Battle Cruiser GEE-M
Pilot: Commander T'Putz
Components:
Armor: [ ]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ]
Shield Generator: [4][1]

2023-04-10 16:42:10 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:10 INFO  CombatController:536 - Small Seeker Missile from Klingon Battle Cruiser targeting Enterprise at Medium range and closing
2023-04-10 16:42:10 INFO  CombatController:270 - ********** Starting Round 3
2023-04-10 16:42:10 INFO  Ship:585 - Initiative roll Enterprise: (11) + 2 = 13
2023-04-10 16:42:10 INFO  Ship:585 - Initiative roll Klingon Battle Cruiser: (20) + -2 = 18
2023-04-10 16:42:10 DEBUG CombatController:405 - Status: ShipToShipRelation[Enterprise]..[Klingon Battle Cruiser]
2023-04-10 16:42:10 DEBUG Ship:784 - Comparing Klingon Battle Cruiser to Enterprise
2023-04-10 16:42:10 INFO  CombatController:467 - Klingon Battle Cruiser (HP:55 Shields:5 Armor:1)
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«MÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Move action.
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«SÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Standard action.
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«RÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Reaction action.
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Missile Launcher at Enterprise: (17) + 1 + 0 + 0 + -2 = 16 vs. TL8 -> Hit!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Energy Weapon at Enterprise: (5) + 1 + 0 + 0 + -2 = 4 vs. TL8 -> Miss
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Kinetic Weapon at Enterprise: (7) + 1 + 0 + 0 + -2 = 6 vs. TL8 -> Miss
2023-04-10 16:42:10 INFO  CombatController:467 - Enterprise (HP:73 Shields:0 Armor:2)
2023-04-10 16:42:10 DEBUG Dice:22 - coin flip = tails
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Missile Launcher at Klingon Battle Cruiser: (9) + 20 + 0 + 0 + -2 = 27 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Energy Weapon at Klingon Battle Cruiser: (11) + 20 + 0 + 0 + -2 = 29 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 2
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 2 damage
2023-04-10 16:42:10 INFO  Ship:104 - Shield Generator 1 damaged for 2 points!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Kinetic Weapon at Klingon Battle Cruiser: (3) + 20 + 0 + 0 + -2 = 21 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 3
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 3
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 3 damage
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Enterprise)
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Guided Missile (Enterprise)
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Guided Missile (Enterprise)
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Klingon Battle Cruiser)
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Guided Missile (Klingon Battle Cruiser)
2023-04-10 16:42:10 INFO  CombatController:529 - ********** End of Round 3
2023-04-10 16:42:10 INFO  CombatController:532 - Ship Status:
Enterprise (HP:73 Shields:0 Armor:2)
Captain: Enterprise GEE-M
Pilot: Enterprise GEE-M
Components:
A.I. Module: [ ][ ][ ][ ][ ]
Armor: [ ][ ]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ][ ][ ]
Shield Generator: [X][X]

2023-04-10 16:42:10 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:10 INFO  CombatController:536 - Small Seeker Missile from Enterprise targeting Klingon Battle Cruiser at Short range and closing
2023-04-10 16:42:10 INFO  CombatController:536 - Small Guided Missile from Enterprise targeting Klingon Battle Cruiser at Short range and closing
2023-04-10 16:42:10 INFO  CombatController:536 - Small Guided Missile from Enterprise targeting Klingon Battle Cruiser at Short range and closing
2023-04-10 16:42:10 INFO  CombatController:532 - Ship Status:
Klingon Battle Cruiser (HP:55 Shields:3 Armor:1)
Captain: Klingon Battle Cruiser GEE-M
Pilot: Commander T'Putz
Components:
Armor: [ ]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ]
Shield Generator: [2][1]

2023-04-10 16:42:10 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:10 INFO  CombatController:536 - Small Seeker Missile from Klingon Battle Cruiser targeting Enterprise at Short range and closing
2023-04-10 16:42:10 INFO  CombatController:536 - Small Guided Missile from Klingon Battle Cruiser targeting Enterprise at Short range and closing
2023-04-10 16:42:10 INFO  CombatController:270 - ********** Starting Round 4
2023-04-10 16:42:10 INFO  Ship:585 - Initiative roll Enterprise: (18) + 2 = 20
2023-04-10 16:42:10 INFO  Ship:585 - Initiative roll Klingon Battle Cruiser: (14) + -2 = 12
2023-04-10 16:42:10 DEBUG CombatController:405 - Status: ShipToShipRelation[Enterprise].[Klingon Battle Cruiser]
2023-04-10 16:42:10 DEBUG Ship:784 - Comparing Klingon Battle Cruiser to Enterprise
2023-04-10 16:42:10 INFO  CombatController:467 - Enterprise (HP:73 Shields:0 Armor:2)
2023-04-10 16:42:10 DEBUG Dice:22 - coin flip = heads
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Missile Launcher at Klingon Battle Cruiser: (19) + 20 + 0 + 0 + 0 = 39 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Energy Weapon at Klingon Battle Cruiser: (20) + 20 + 0 + 0 + 0 = 40 vs. TL9 -> Hit!
2023-04-10 16:42:10 INFO  Weapon:86 - Critical Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 5
2023-04-10 16:42:10 DEBUG Weapon:132 - crit damage = 1
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 6
2023-04-10 16:42:10 INFO  Ship:85 - Shields absorb 3 damage
2023-04-10 16:42:10 INFO  Ship:104 - Shield Generator 1 damaged for 2 points!
2023-04-10 16:42:10 INFO  Ship:107 - Shield Generator 1 is disabled by battle damage!
2023-04-10 16:42:10 INFO  Ship:104 - Shield Generator 2 damaged for 1 points!
2023-04-10 16:42:10 INFO  Ship:107 - Shield Generator 2 is disabled by battle damage!
2023-04-10 16:42:10 INFO  Ship:134 - Armor absorbs 1 damage
2023-04-10 16:42:10 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:10 INFO  Ship:183 - Hull absorbs 2 damage
2023-04-10 16:42:10 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 2 damage!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 9 + 0 = 9
2023-04-10 16:42:10 INFO  Weapon:328 - Enterprise fires Small Kinetic Weapon at Klingon Battle Cruiser: (12) + 20 + 0 + 0 + 0 = 32 vs. TL9 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 3
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 3
2023-04-10 16:42:10 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 INFO  Ship:134 - Armor absorbs 1 damage
2023-04-10 16:42:10 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:10 INFO  Ship:183 - Hull absorbs 2 damage
2023-04-10 16:42:10 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 2 damage!
2023-04-10 16:42:10 INFO  CombatController:467 - Klingon Battle Cruiser (HP:51 Shields:0 Armor:1)
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«MÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Move action.
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«SÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Standard action.
2023-04-10 16:42:10 DEBUG CombatController:474 - Commander T'Putz attempting Â«RÂ»No Action:Commander T'Putz
2023-04-10 16:42:10 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Reaction action.
2023-04-10 16:42:10 DEBUG Dice:22 - coin flip = tails
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Missile Launcher at Enterprise: (19) + 1 + 0 + 0 + 0 = 20 vs. TL8 -> Hit!
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Energy Weapon at Enterprise: (3) + 1 + 0 + 0 + 0 = 4 vs. TL8 -> Miss
2023-04-10 16:42:10 DEBUG Ship:235 - TL = 8 + 0 = 8
2023-04-10 16:42:10 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Kinetic Weapon at Enterprise: (12) + 1 + 0 + 0 + 0 = 13 vs. TL8 -> Hit!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 5
2023-04-10 16:42:10 DEBUG Weapon:58 - raw damage = 5
2023-04-10 16:42:10 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 INFO  Ship:134 - Armor absorbs 2 damage
2023-04-10 16:42:10 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:10 INFO  Ship:183 - Hull absorbs 3 damage
2023-04-10 16:42:10 INFO  Ship:193 - Enterprise hull hit for 3 damage!
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Enterprise)
2023-04-10 16:42:10 INFO  Missile:62 - Small Seeker Missile hits Klingon Battle Cruiser!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:10 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 INFO  Ship:134 - Armor absorbs 1 damage
2023-04-10 16:42:10 INFO  Ship:153 - Armor 1 damaged for 1 points!
2023-04-10 16:42:10 INFO  Ship:156 - Armor 1 is disabled by battle damage!
2023-04-10 16:42:10 INFO  Ship:183 - Hull absorbs 1 damage
2023-04-10 16:42:10 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 1 damage!
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Guided Missile (Enterprise)
2023-04-10 16:42:10 INFO  Missile:62 - Small Guided Missile hits Klingon Battle Cruiser!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 3
2023-04-10 16:42:10 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:10 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:10 INFO  Ship:183 - Hull absorbs 3 damage
2023-04-10 16:42:10 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 3 damage!
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Guided Missile (Enterprise)
2023-04-10 16:42:10 INFO  Missile:62 - Small Guided Missile hits Klingon Battle Cruiser!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 3
2023-04-10 16:42:10 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:10 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:10 INFO  Ship:183 - Hull absorbs 3 damage
2023-04-10 16:42:10 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 3 damage!
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Enterprise)
2023-04-10 16:42:10 INFO  Missile:62 - Small Seeker Missile hits Klingon Battle Cruiser!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 4
2023-04-10 16:42:10 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:10 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:10 INFO  Ship:183 - Hull absorbs 4 damage
2023-04-10 16:42:10 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 4 damage!
2023-04-10 16:42:10 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Klingon Battle Cruiser)
2023-04-10 16:42:10 INFO  Missile:62 - Small Seeker Missile hits Enterprise!
2023-04-10 16:42:10 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:10 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:10 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:10 INFO  Ship:134 - Armor absorbs 2 damage
2023-04-10 16:42:10 INFO  Ship:153 - Armor 2 damaged for 1 points!
2023-04-10 16:42:10 INFO  Ship:156 - Armor 2 is disabled by battle damage!
2023-04-10 16:42:11 INFO  Ship:153 - Armor 1 damaged for 1 points!
2023-04-10 16:42:11 INFO  Ship:156 - Armor 1 is disabled by battle damage!
2023-04-10 16:42:11 DEBUG CombatController:418 - Moving missile Small Guided Missile (Klingon Battle Cruiser)
2023-04-10 16:42:11 INFO  Missile:62 - Small Guided Missile hits Enterprise!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 4
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 4 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 4 damage!
2023-04-10 16:42:11 DEBUG CombatController:418 - Moving missile Small Guided Missile (Klingon Battle Cruiser)
2023-04-10 16:42:11 INFO  Missile:62 - Small Guided Missile hits Enterprise!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 4
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 4 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 4 damage!
2023-04-10 16:42:11 INFO  CombatController:529 - ********** End of Round 4
2023-04-10 16:42:11 INFO  CombatController:532 - Ship Status:
Enterprise (HP:62 Shields:0 Armor:0)
Captain: Enterprise GEE-M
Pilot: Enterprise GEE-M
Components:
A.I. Module: [ ][ ][ ][ ][ ]
Armor: [X][X]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ][ ][ ]
Shield Generator: [X][X]

2023-04-10 16:42:11 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:11 INFO  CombatController:532 - Ship Status:
Klingon Battle Cruiser (HP:40 Shields:0 Armor:0)
Captain: Klingon Battle Cruiser GEE-M
Pilot: Commander T'Putz
Components:
Armor: [X]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ]
Shield Generator: [X][X]

2023-04-10 16:42:11 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:11 INFO  CombatController:270 - ********** Starting Round 5
2023-04-10 16:42:11 INFO  Ship:585 - Initiative roll Enterprise: (12) + 4 = 16
2023-04-10 16:42:11 INFO  Ship:585 - Initiative roll Klingon Battle Cruiser: (8) + -1 = 7
2023-04-10 16:42:11 DEBUG CombatController:405 - Status: ShipToShipRelation[Enterprise][Klingon Battle Cruiser]
2023-04-10 16:42:11 DEBUG Ship:784 - Comparing Klingon Battle Cruiser to Enterprise
2023-04-10 16:42:11 INFO  CombatController:467 - Enterprise (HP:62 Shields:0 Armor:0)
2023-04-10 16:42:11 DEBUG Dice:22 - coin flip = heads
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Missile Launcher at Klingon Battle Cruiser: (16) + 20 + 0 + 0 + 2 = 38 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Energy Weapon at Klingon Battle Cruiser: (19) + 20 + 0 + 0 + 2 = 41 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 4
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 4
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 4 damage
2023-04-10 16:42:11 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 4 damage!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Kinetic Weapon at Klingon Battle Cruiser: (20) + 20 + 0 + 0 + 2 = 42 vs. TL10 -> Hit!
2023-04-10 16:42:11 INFO  Weapon:86 - Critical Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 6
2023-04-10 16:42:11 DEBUG Weapon:132 - crit damage = 5
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 11
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 11 damage
2023-04-10 16:42:11 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 11 damage!
2023-04-10 16:42:11 INFO  CombatController:467 - Klingon Battle Cruiser (HP:25 Shields:0 Armor:0)
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«MÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Move action.
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«SÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Standard action.
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«RÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Reaction action.
2023-04-10 16:42:11 DEBUG Dice:22 - coin flip = heads
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Missile Launcher at Enterprise: (4) + 1 + 0 + 0 + 2 = 7 vs. TL10 -> Miss
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Energy Weapon at Enterprise: (14) + 1 + 0 + 0 + 2 = 17 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 3
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 3
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 3 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 3 damage!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Kinetic Weapon at Enterprise: (12) + 1 + 0 + 0 + 2 = 15 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 3
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 3
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 3 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 3 damage!
2023-04-10 16:42:11 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Enterprise)
2023-04-10 16:42:11 INFO  Missile:62 - Small Seeker Missile hits Klingon Battle Cruiser!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 4
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 4 damage
2023-04-10 16:42:11 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 4 damage!
2023-04-10 16:42:11 INFO  CombatController:529 - ********** End of Round 5
2023-04-10 16:42:11 INFO  CombatController:532 - Ship Status:
Enterprise (HP:56 Shields:0 Armor:0)
Captain: Enterprise GEE-M
Pilot: Enterprise GEE-M
Components:
A.I. Module: [ ][ ][ ][ ][ ]
Armor: [X][X]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ][ ][ ]
Shield Generator: [X][X]

2023-04-10 16:42:11 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:11 INFO  CombatController:532 - Ship Status:
Klingon Battle Cruiser (HP:21 Shields:0 Armor:0)
Captain: Klingon Battle Cruiser GEE-M
Pilot: Commander T'Putz
Components:
Armor: [X]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ]
Shield Generator: [X][X]

2023-04-10 16:42:11 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:11 INFO  CombatController:270 - ********** Starting Round 6
2023-04-10 16:42:11 INFO  Ship:585 - Initiative roll Enterprise: (18) + 4 = 22
2023-04-10 16:42:11 INFO  Ship:585 - Initiative roll Klingon Battle Cruiser: (14) + -1 = 13
2023-04-10 16:42:11 DEBUG ShipToShipRelation:123 - Enterprise and Klingon Battle Cruiser retain status quo.
2023-04-10 16:42:11 DEBUG CombatController:405 - Status: ShipToShipRelation[Enterprise][Klingon Battle Cruiser]
2023-04-10 16:42:11 DEBUG Ship:784 - Comparing Klingon Battle Cruiser to Enterprise
2023-04-10 16:42:11 INFO  CombatController:467 - Enterprise (HP:56 Shields:0 Armor:0)
2023-04-10 16:42:11 DEBUG Dice:22 - coin flip = heads
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Missile Launcher at Klingon Battle Cruiser: (3) + 20 + 0 + 0 + 2 = 25 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Energy Weapon at Klingon Battle Cruiser: (1) + 20 + 0 + 0 + 2 = 23 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 6
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 6
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 6 damage
2023-04-10 16:42:11 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 6 damage!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Kinetic Weapon at Klingon Battle Cruiser: (7) + 20 + 0 + 0 + 2 = 29 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 7
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 7
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 7 damage
2023-04-10 16:42:11 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 7 damage!
2023-04-10 16:42:11 INFO  CombatController:467 - Klingon Battle Cruiser (HP:8 Shields:0 Armor:0)
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«MÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Move action.
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«SÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Standard action.
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«RÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Reaction action.
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Missile Launcher at Enterprise: (15) + 1 + 0 + 0 + 2 = 18 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Energy Weapon at Enterprise: (11) + 1 + 0 + 0 + 2 = 14 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 2
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 2 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 2 damage!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Kinetic Weapon at Enterprise: (20) + 1 + 0 + 0 + 2 = 23 vs. TL10 -> Hit!
2023-04-10 16:42:11 INFO  Weapon:86 - Critical Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 5
2023-04-10 16:42:11 DEBUG Weapon:132 - crit damage = 6
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 11
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 11 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 11 damage!
2023-04-10 16:42:11 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Enterprise)
2023-04-10 16:42:11 INFO  Missile:62 - Small Seeker Missile hits Klingon Battle Cruiser!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 1
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 1 damage
2023-04-10 16:42:11 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 1 damage!
2023-04-10 16:42:11 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Klingon Battle Cruiser)
2023-04-10 16:42:11 INFO  Missile:62 - Small Seeker Missile hits Enterprise!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 2 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 2 damage!
2023-04-10 16:42:11 INFO  CombatController:529 - ********** End of Round 6
2023-04-10 16:42:11 INFO  CombatController:532 - Ship Status:
Enterprise (HP:41 Shields:0 Armor:0)
Captain: Enterprise GEE-M
Pilot: Enterprise GEE-M
Components:
A.I. Module: [ ][ ][ ][ ][ ]
Armor: [X][X]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ][ ][ ]
Shield Generator: [X][X]

2023-04-10 16:42:11 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:11 INFO  CombatController:532 - Ship Status:
Klingon Battle Cruiser (HP:7 Shields:0 Armor:0)
Captain: Klingon Battle Cruiser GEE-M
Pilot: Commander T'Putz
Components:
Armor: [X]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ]
Shield Generator: [X][X]

2023-04-10 16:42:11 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:11 INFO  CombatController:270 - ********** Starting Round 7
2023-04-10 16:42:11 INFO  Ship:585 - Initiative roll Enterprise: (2) + 4 = 6
2023-04-10 16:42:11 INFO  Ship:585 - Initiative roll Klingon Battle Cruiser: (14) + -1 = 13
2023-04-10 16:42:11 DEBUG ShipToShipRelation:123 - Enterprise and Klingon Battle Cruiser retain status quo.
2023-04-10 16:42:11 DEBUG CombatController:405 - Status: ShipToShipRelation[Enterprise][Klingon Battle Cruiser]
2023-04-10 16:42:11 DEBUG Ship:784 - Comparing Klingon Battle Cruiser to Enterprise
2023-04-10 16:42:11 INFO  CombatController:467 - Klingon Battle Cruiser (HP:7 Shields:0 Armor:0)
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«MÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Move action.
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«SÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Standard action.
2023-04-10 16:42:11 DEBUG CombatController:474 - Commander T'Putz attempting Â«RÂ»No Action:Commander T'Putz
2023-04-10 16:42:11 INFO  PlayerIntentNoAction:15 - Commander T'Putz does not use their Reaction action.
2023-04-10 16:42:11 DEBUG Dice:22 - coin flip = heads
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Missile Launcher at Enterprise: (20) + 1 + 0 + 0 + 2 = 23 vs. TL10 -> Hit!
2023-04-10 16:42:11 INFO  Weapon:86 - Critical Hit!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Energy Weapon at Enterprise: (18) + 1 + 0 + 0 + 2 = 21 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 3
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 3
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 3 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 3 damage!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Klingon Battle Cruiser fires Small Kinetic Weapon at Enterprise: (3) + 1 + 0 + 0 + 2 = 6 vs. TL10 -> Miss
2023-04-10 16:42:11 INFO  CombatController:467 - Enterprise (HP:38 Shields:0 Armor:0)
2023-04-10 16:42:11 DEBUG Dice:22 - coin flip = heads
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Missile Launcher at Klingon Battle Cruiser: (19) + 20 + 0 + 0 + 2 = 41 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Energy Weapon at Klingon Battle Cruiser: (8) + 20 + 0 + 0 + 2 = 30 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 1
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 1
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 1 damage
2023-04-10 16:42:11 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 1 damage!
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 10 + 0 = 10
2023-04-10 16:42:11 INFO  Weapon:328 - Enterprise fires Small Kinetic Weapon at Klingon Battle Cruiser: (19) + 20 + 0 + 0 + 2 = 41 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 7
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 7
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 6 damage
2023-04-10 16:42:11 INFO  Ship:193 - Klingon Battle Cruiser hull hit for 6 damage!
2023-04-10 16:42:11 DEBUG Ship:202 - Klingon Battle Cruiser is disabled by hull damage
2023-04-10 16:42:11 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Enterprise)
2023-04-10 16:42:11 INFO  Missile:62 - Small Seeker Missile hits Klingon Battle Cruiser!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:187 - Damage is unaffected by hull
2023-04-10 16:42:11 DEBUG Ship:197 - Hull is unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:202 - Klingon Battle Cruiser is disabled by hull damage
2023-04-10 16:42:11 DEBUG CombatController:418 - Moving missile Small Seeker Missile (Klingon Battle Cruiser)
2023-04-10 16:42:11 INFO  Missile:62 - Small Seeker Missile hits Enterprise!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:11 DEBUG Weapon:132 - crit damage = 3
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 5 damage
2023-04-10 16:42:11 INFO  Ship:193 - Enterprise hull hit for 5 damage!
2023-04-10 16:42:11 INFO  CombatController:529 - ********** End of Round 7
2023-04-10 16:42:11 INFO  CombatController:532 - Ship Status:
Enterprise (HP:33 Shields:0 Armor:0)
Captain: Enterprise GEE-M
Pilot: Enterprise GEE-M
Components:
A.I. Module: [ ][ ][ ][ ][ ]
Armor: [X][X]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ][ ][ ]
Shield Generator: [X][X]

2023-04-10 16:42:11 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:11 INFO  CombatController:532 - Ship Status:
Klingon Battle Cruiser (HP:0 Shields:0 Armor:0)
Captain: Klingon Battle Cruiser GEE-M
Pilot: Commander T'Putz
Components:
Armor: [X]
Energy Weapon: [ ]
Kinetic Weapon: [ ]
Missile Launcher: [ ]
Power Core: [ ]
Shield Generator: [X][X]

2023-04-10 16:42:11 INFO  CombatController:533 - Active missiles:
2023-04-10 16:42:11 INFO  CombatController:553 - Enterprise wins!
2023-04-10 16:42:11 INFO  CombatController:561 - Combat complete after 7 rounds. Final status:
2023-04-10 16:42:11 INFO  CombatController:564 - Enterprise (HP:33 Shields:0 Armor:0)
2023-04-10 16:42:11 INFO  CombatController:564 - Klingon Battle Cruiser (HP:0 Shields:0 Armor:0)
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.691 s - in com.dougcrews.game.starfinder.controller.CombatControllerTest
[INFO] Running com.dougcrews.game.starfinder.model.DistanceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.096 s - in com.dougcrews.game.starfinder.model.DistanceTest
[INFO] Running com.dougcrews.game.starfinder.model.intent.PlayerIntentCalledShotTest
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 12 + 0 = 12
2023-04-10 16:42:11 INFO  Weapon:328 - Default Ship fires Small Unit Test Weapon at Engine: (2) + 20 + 0 + 0 + 2 = 24 vs. TL10 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 2
2023-04-10 16:42:11 INFO  ShipComponent:323 - Engine absorbs 2 damage
2023-04-10 16:42:11 INFO  ShipComponent:332 - Engine hit for 2 damage!
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.114 s - in com.dougcrews.game.starfinder.model.intent.PlayerIntentCalledShotTest
[INFO] Running com.dougcrews.game.starfinder.model.intent.PlayerIntentReroutePowerTest
2023-04-10 16:42:11 INFO  PlayerIntentComponentReroutePower:22 - Default Ship GEE-M attempts to reroute power from Unit Test Component 1 to Unit Test Component 2
2023-04-10 16:42:11 WARN  PlayerIntentComponentReroutePower:32 - Unit Test Component 1 is inactive.
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.033 s - in com.dougcrews.game.starfinder.model.intent.PlayerIntentReroutePowerTest
[INFO] Running com.dougcrews.game.starfinder.model.intent.PlayerIntentTest
2023-04-10 16:42:11 DEBUG Player:90 - Replacing unused intent Â«MÂ»No Action:Joe UnitTest with Â«MÂ»Unit Test Move PlayerIntent:Joe UnitTest
2023-04-10 16:42:11 DEBUG Player:90 - Replacing unused intent Â«SÂ»No Action:Joe UnitTest with Â«SÂ»Unit Test Standard PlayerIntent:Joe UnitTest
2023-04-10 16:42:11 DEBUG Player:90 - Replacing unused intent Â«MÂ»No Action:Joe UnitTest with Â«MÂ»Unit Test Move PlayerIntent:Joe UnitTest
2023-04-10 16:42:11 DEBUG Player:90 - Replacing unused intent Â«RÂ»No Action:Joe UnitTest with Â«RÂ»Unit Test ReactionIntent:Joe UnitTest
2023-04-10 16:42:11 DEBUG Player:90 - Replacing unused intent Â«SÂ»No Action:Joe UnitTest with Â«MÂ»Unit Test Move PlayerIntent:Joe UnitTest
2023-04-10 16:42:11 DEBUG Player:90 - Replacing unused intent Â«SÂ»No Action:Joe UnitTest with Â«SÂ»Unit Test Standard PlayerIntent:Joe UnitTest
2023-04-10 16:42:11 DEBUG Player:90 - Replacing unused intent Â«MÂ»No Action:Joe UnitTest with Â«MÂ»Unit Test Move PlayerIntent:Joe UnitTest
2023-04-10 16:42:11 DEBUG Player:90 - Replacing unused intent Â«RÂ»No Action:Joe UnitTest with Â«RÂ»Unit Test ReactionIntent:Joe UnitTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.069 s - in com.dougcrews.game.starfinder.model.intent.PlayerIntentTest
[INFO] Running com.dougcrews.game.starfinder.model.intent.PlayerIntentWeaponFireTest
2023-04-10 16:42:11 DEBUG Ship:235 - TL = 12 + 0 = 12
2023-04-10 16:42:11 INFO  Weapon:328 - Default Ship fires Small Unit Test Weapon at Default Ship: (7) + 20 + 0 + 0 + 2 = 29 vs. TL12 -> Hit!
2023-04-10 16:42:11 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:11 DEBUG Weapon:58 - raw damage = 2
2023-04-10 16:42:11 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:11 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:11 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:11 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:11 INFO  Ship:183 - Hull absorbs 2 damage
2023-04-10 16:42:11 INFO  Ship:193 - Default Ship hull hit for 2 damage!
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.04 s - in com.dougcrews.game.starfinder.model.intent.PlayerIntentWeaponFireTest
[INFO] Running com.dougcrews.game.starfinder.model.PlayerTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.063 s - in com.dougcrews.game.starfinder.model.PlayerTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.AIModuleTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.035 s - in com.dougcrews.game.starfinder.model.ship.component.AIModuleTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.ArmorTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.051 s - in com.dougcrews.game.starfinder.model.ship.component.ArmorTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.EngineTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.034 s - in com.dougcrews.game.starfinder.model.ship.component.EngineTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.FlakTest
2023-04-10 16:42:12 INFO  MessageController:21 - Unit Test Gunner fires Flak Weapon at Small Seeker Missile: (3) + 20 + 0 vs. 21 => Success!
2023-04-10 16:42:12 INFO  Flak:75 - Small Seeker Missile explodes harmlessly!
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.037 s - in com.dougcrews.game.starfinder.model.ship.component.FlakTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.PowerCoreTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.011 s - in com.dougcrews.game.starfinder.model.ship.component.PowerCoreTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.ShipComponentTest
2023-04-10 16:42:12 INFO  Ship:304 - Default Ship Sensor Array 4 shuts down due to brownout!
2023-04-10 16:42:12 INFO  Ship:304 - Default Ship Sensor Array 1 shuts down due to brownout!
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.083 s - in com.dougcrews.game.starfinder.model.ship.component.ShipComponentTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.weapon.EnergyWeaponTest
2023-04-10 16:42:12 INFO  Ship:85 - Shields absorb 1 damage
2023-04-10 16:42:12 INFO  Ship:104 - Shield Generator 1 damaged for 1 points!
2023-04-10 16:42:12 INFO  Ship:85 - Shields absorb 4 damage
2023-04-10 16:42:12 INFO  Ship:104 - Shield Generator 1 damaged for 4 points!
2023-04-10 16:42:12 INFO  Ship:107 - Shield Generator 1 is disabled by battle damage!
2023-04-10 16:42:12 INFO  Ship:134 - Armor absorbs 6 damage
2023-04-10 16:42:12 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:12 INFO  Ship:183 - Hull absorbs 1 damage
2023-04-10 16:42:12 INFO  Ship:193 - Default Ship hull hit for 1 damage!
2023-04-10 16:42:12 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:12 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:12 INFO  Ship:134 - Armor absorbs 6 damage
2023-04-10 16:42:12 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:12 INFO  Ship:183 - Hull absorbs 54 damage
2023-04-10 16:42:12 INFO  Ship:193 - Default Ship hull hit for 54 damage!
2023-04-10 16:42:12 DEBUG Ship:202 - Default Ship is disabled by hull damage
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.12 s - in com.dougcrews.game.starfinder.model.ship.component.weapon.EnergyWeaponTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.weapon.KineticWeaponTest
2023-04-10 16:42:12 INFO  Ship:85 - Shields absorb 1 damage
2023-04-10 16:42:12 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:12 INFO  Ship:85 - Shields absorb 5 damage
2023-04-10 16:42:12 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:12 INFO  Ship:134 - Armor absorbs 6 damage
2023-04-10 16:42:12 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:12 INFO  Ship:183 - Hull absorbs 1 damage
2023-04-10 16:42:12 INFO  Ship:193 - Default Ship hull hit for 1 damage!
2023-04-10 16:42:12 INFO  Ship:85 - Shields absorb 5 damage
2023-04-10 16:42:12 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:12 INFO  Ship:134 - Armor absorbs 6 damage
2023-04-10 16:42:12 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:12 INFO  Ship:183 - Hull absorbs 54 damage
2023-04-10 16:42:12 INFO  Ship:193 - Default Ship hull hit for 54 damage!
2023-04-10 16:42:12 DEBUG Ship:202 - Default Ship is disabled by hull damage
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.167 s - in com.dougcrews.game.starfinder.model.ship.component.weapon.KineticWeaponTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncherTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.058 s - in com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncherTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.component.weapon.MissileTest
2023-04-10 16:42:12 INFO  Missile:49 - Small Seeker Missile passes out of range.
2023-04-10 16:42:12 INFO  Missile:62 - Small Guided Missile hits Default Ship!
2023-04-10 16:42:12 DEBUG Weapon:113 - raw damage = 4
2023-04-10 16:42:12 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:12 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:12 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:12 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:12 INFO  Ship:183 - Hull absorbs 4 damage
2023-04-10 16:42:12 INFO  Ship:193 - Default Ship hull hit for 4 damage!
2023-04-10 16:42:12 INFO  ShipComponent:20 - Small Seeker Missile from Default Ship loses Target Lock and flies away harmlessly!
2023-04-10 16:42:12 INFO  Missile:62 - Small Seeker Missile hits Default Ship!
2023-04-10 16:42:12 DEBUG Weapon:113 - raw damage = 1
2023-04-10 16:42:12 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:12 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:12 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:12 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:12 INFO  Ship:183 - Hull absorbs 1 damage
2023-04-10 16:42:12 INFO  Ship:193 - Default Ship hull hit for 1 damage!
2023-04-10 16:42:12 INFO  Missile:62 - Small Guided Missile hits Default Ship!
2023-04-10 16:42:12 DEBUG Weapon:113 - raw damage = 2
2023-04-10 16:42:12 DEBUG Ship:89 - Damage is unaffected by shields
2023-04-10 16:42:12 DEBUG Ship:114 - Shields are unaffected by damage
2023-04-10 16:42:12 DEBUG Ship:138 - Damage is unaffected by armor
2023-04-10 16:42:12 DEBUG Ship:163 - Armor is unaffected by damage
2023-04-10 16:42:12 INFO  Ship:183 - Hull absorbs 2 damage
2023-04-10 16:42:12 INFO  Ship:193 - Default Ship hull hit for 2 damage!
2023-04-10 16:42:12 INFO  ShipComponent:20 - Small Seeker Missile from Default Ship loses Target Lock and flies away harmlessly!
2023-04-10 16:42:12 INFO  ShipComponent:20 - Small Seeker Missile from Default Ship loses Target Lock and flies away harmlessly!
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.099 s - in com.dougcrews.game.starfinder.model.ship.component.weapon.MissileTest
[INFO] Running com.dougcrews.game.starfinder.model.ship.ShipTest
2023-04-10 16:42:12 INFO  Ship:304 - Default Ship Passive Sensors 1 shuts down due to brownout!
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.06 s - in com.dougcrews.game.starfinder.model.ship.ShipTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 73, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  16.454 s
[INFO] Finished at: 2023-04-10T16:42:14-07:00
[INFO] ------------------------------------------------------------------------

```

