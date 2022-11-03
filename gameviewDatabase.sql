CREATE DATABASE  IF NOT EXISTS `gameview` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gameview`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: gameview
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment_table`
--

DROP TABLE IF EXISTS `comment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_table` (
  `post_id` mediumint NOT NULL,
  `game_id` mediumint NOT NULL,
  `user_id` mediumint NOT NULL,
  `totallike` int NOT NULL DEFAULT '0',
  `totaldislike` int NOT NULL DEFAULT '0',
  `totallove` int NOT NULL DEFAULT '0',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `createtime` datetime NOT NULL,
  `is_get_coin` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`post_id`) USING BTREE,
  KEY `game_id_idx` (`game_id`) USING BTREE,
  KEY `user_id_idx` (`user_id`) USING BTREE,
  CONSTRAINT `game_id` FOREIGN KEY (`game_id`) REFERENCES `game_table` (`game_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `profile_table` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_table`
--

LOCK TABLES `comment_table` WRITE;
/*!40000 ALTER TABLE `comment_table` DISABLE KEYS */;
INSERT INTO `comment_table` VALUES (1,21,5,7,3,1,'very nice!!!!','2022-10-13 00:00:00',0),(2,21,5,5,0,0,'bucuo','2022-10-13 00:00:00',0),(3,5,6,0,0,0,'wow','2022-10-15 00:00:00',0),(4,2,2,2,0,0,'This game made me so miserable .','2022-10-11 12:15:57',0),(5,2,3,8,0,0,'The worst thing about this game is that we\'ll never get to experience it for the first time again.','2022-09-15 15:15:45',0),(6,2,5,2,0,1,'Imagine you\'re working in a 9-5 corporate job, you\'re done with your work, you come back home, log onto Steam, see your games list for 5 seconds, buy a game on sale that you\'re never going to play and then switch off your PC and go to a bar. While having a drink, you think of the good times when games used to be fun and authentic. But then you tell yourself, the problem isn\'t the games, its you. \"Its not that games aren\'t fun anymore, you have gotten older\".','2022-09-15 15:15:45',0),(7,2,4,3,0,0,'This game made me so miserable .','2022-08-14 15:14:35',0),(8,2,3,4,0,1,'Stop scrolling for bad reviews just buy this masterpiece already!','2022-09-12 12:14:59',0),(9,24,1,3,0,0,'I had a blast! Even after 110h I\'m not bored of it. Crazy level design and boss fights. Fair, but ruthless and unforgiving gameplay. Deep and interesting lore like always. Fromsoft really went all out with this one. The freedom to walk away from a boss fight and go do something else really elevates this series to another level. I think it is my favorite Fromsoft game honestly.','2022-10-11 12:15:57',0),(10,24,4,3,0,1,'All that said, the story is amazing, the quests are solid, the world is insane. Solid 8/10, would be 10/10 if bosses weren\'t R1 spambots to push players into using spirit ashes.','2022-04-15 13:14:52',0),(11,24,1,0,0,0,'you can hug a woman','2022-10-11 12:15:57',0),(12,24,2,1,0,1,'it ruined my life and I have no one to talk to 10\\10','2022-04-13 15:24:51',0),(13,14,5,0,0,0,'Simple. Beautiful. Brutal. Challenging. Harshly punishable and rewarding.','2021-12-15 15:14:57',0),(14,24,3,0,0,0,'Sekiro is an action-adventure that emphasizes precision and skill in its combat that walks the line between deliberate, patient, stealthy, and astounding melee combat against threats. Its flexible tools support a more focused experience, Sekiro is an amazing game that can stand on its own alongside its predecessors.','2022-08-14 15:14:35',0),(15,14,2,0,0,0,'I won\'t really be discussing the difficulty curve or how it is different or similar to other games from \"FROM SOFTWARE\". My review solely based on this game as an individual project and the experience that I had.','2021-09-15 15:14:32',0),(16,13,3,0,0,0,'You\'re like a domesticated cat that is allowed outside. You venture out, destroy an ecosystem, and return home to the hub for food.','2022-11-12 14:20:52',0),(17,6,5,0,0,0,'What happened to this franchise was a crime, and the fact we\'ll never get a true successor or remaster because of EA will haunt me until the day I die. We won\'t even get the VS mode on PC, god.','2022-08-14 15:14:35',0),(18,3,1,0,0,0,'The note said \"chest ahead\" at the edge of a cliff with nothing in sight...there were plenty of bloodstains but they must\'ve missed the jump... I had to be sure','2022-09-12 12:14:59',0),(19,4,3,0,0,0,'You don\'t lose when you die, you lose when you quit','2022-08-14 15:14:35',0),(20,5,3,0,0,0,'if you die in game you die in real life','2022-08-14 15:14:35',0),(21,6,5,0,0,0,'Please bring back this level of gaming.','2022-10-11 12:15:57',0),(22,7,2,0,0,0,'If you rush you can make it the hardest Souls game you\'ve ever played.','2021-09-12 15:50:47',0),(23,8,4,0,0,0,'Every time I hop on this game i either have a great time or am contemplating suicide. Best game ive ever played.','2022-12-01 14:20:17',0),(24,9,4,0,0,0,'buy me the game im a cute girl','2019-12-08 17:12:15',0),(25,10,3,0,0,0,' it wasn\'t as daunting as I\'d thought it\'d be, and it actually ended up becoming one of my favorite games.','2022-12-01 14:20:17',0),(26,11,4,0,0,0,'A near perfect game that needs some performance improvements','2022-09-15 15:15:45',0),(27,12,5,3,0,0,'I have a crush on blaidd but no one will know because my review will be lost in the pile of reviews.','2021-12-14 15:47:51',0),(28,13,2,0,0,0,'I have a crush on blaidd but no one will know because my review will be lost in the pile of reviews.','2022-10-11 12:15:57',0),(29,14,3,0,0,0,'There are 168 hours in a week.','2022-08-14 15:14:35',0),(30,15,2,0,0,0,'Almost 200 hours of quality experience... for a SINGLE playtrough!','2022-09-15 15:15:45',0),(31,16,4,0,0,0,'250 hours for me. I tried exploring everything and still missed a ton of stuff','2022-08-14 15:14:35',0),(32,17,4,0,0,0,'best game ever for me!!! i dont play games ..','2021-12-12 14:54:17',0),(33,18,5,0,0,0,'I\'ve sacrificed my schooling, relationship and health for this world.','2021-11-14 15:14:48',0),(34,19,3,0,0,0,'I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it. I hate it. I love it.','2022-10-11 12:15:57',0),(35,20,4,0,0,0,'POSSIBLE FIX FOR PC FRAME RATE','2021-08-21 15:14:21',0),(36,21,2,0,0,0,'This is going to be a long one, go get a cup of coffee if you care enough about it.','2022-09-15 15:15:45',0),(37,22,4,0,0,0,'First, there\'s more to do. More items, quests, and materials to collect means progression feels more meaningful.','2022-09-15 15:15:45',0),(38,23,5,0,0,0,'One of the best gaming experiences I\'ve ever had.','2022-09-12 12:14:59',0),(39,25,4,1,0,0,'I started the journey as a lowly tarnished, with only the guidance of grace to guide me on a perilous path of struggle, hardship and suffering. I stumbled, I failed and I died. I came back up again, learning from my mistakes.','2022-10-11 12:15:57',0),(40,26,3,0,0,0,'While i\'m not far into it, so far it is great. All negative reviews are due to the stuttering. I have a 3070 and I googled a solution.','2021-12-13 15:14:21',0),(41,27,3,0,0,0,'After more than 200+ hours of gameplay i can surely say that this is now my favorite game of all time.','2022-09-14 15:14:57',0),(42,28,4,0,0,0,'An absolute masterclass in game design, sound and storytelling. ','2021-12-13 15:14:21',0),(43,24,3,0,1,0,'Really good !!!!','2022-10-22 04:40:34',0),(44,24,3,0,0,0,'gOOD gAME','2022-10-22 12:00:41',0);
/*!40000 ALTER TABLE `comment_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_table`
--

DROP TABLE IF EXISTS `game_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_table` (
  `game_id` mediumint NOT NULL,
  `gamename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gamepicture` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `totalscore` int NOT NULL DEFAULT '0',
  `number_of_users_rated` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`game_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_table`
--

LOCK TABLES `game_table` WRITE;
/*!40000 ALTER TABLE `game_table` DISABLE KEYS */;
INSERT INTO `game_table` VALUES (1,'Genshin Impact','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/GenshinImpact.jpeg','Role Playing Game','Genshin Impact is an open-world action role-playing game that allows the player to control one of four interchangeable characters in a party. ',0,0),(2,'The Witcher 3','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/TheWitcher.jpeg','Role Playing Game','The game takes place in a fictional fantasy world based on Slavic mythology. Players control Geralt of Rivia, a monster slayer for hire known as a Witcher, and search for his adopted daughter, who is on the run from the otherworldly Wild Hunt. Players battle the game\'s many dangers with weapons and magic, interact with non-player characters, and complete quests to acquire experience points and gold, which are used to increase Geralt\'s abilities and purchase equipment. The game\'s story has three possible endings, determined by the player\'s choices at key points in the narrative.',7,2),(3,'Counter-Strike: Global Offensive','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/CSGO.jpeg','Shooting','The game pits two teams, Terrorists and Counter-Terrorists, against each other in different objective-based game modes. The most common game modes involve the Terrorists planting a bomb while Counter-Terrorists attempt to stop them, or Counter-Terrorists attempting to rescue hostages that the Terrorists have captured. There are nine official game modes, all of which have distinct characteristics specific to that mode. The game also has matchmaking support that allows players to play on dedicated Valve servers, in addition to community-hosted servers with custom maps and game modes. A battle-royale game-mode, \"Danger Zone\", was introduced in December 2018.',0,0),(4,'Overwatch','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/OverWatch.jpeg','Shooting','Overwatch is a 2016 team-based multiplayer first-person shooter game developed and published by Blizzard Entertainment. Described as a \"hero shooter\", Overwatch assigns players into two teams of six, with each player selecting from a large roster of characters, known as \"heroes\", with unique abilities. Teams work to complete map-specific objectives within a limited period of time. Blizzard has added new characters, maps, and game modes post-release, all free of charge, with the only additional cost to players being optional loot boxes to purchase cosmetic items.',8,1),(5,'Civilization 6','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Civilization.jpeg','Strategy','Similar to previous installments, the goal for the player is to develop a civilization from an early settlement through many in-game millennia to become a world power and achieve one of several victory conditions, such as through military domination, technological superiority, or cultural influence, over the other human and computer-controlled opponents. Players do this by exploring the world, founding new cities, building city improvements, deploying military troops to attack and defend from others, researching new technologies and civics advancements, developing an influential culture, and engaging in trade and negotiations with other world leaders.',8,1),(6,'Plants vs. Zombies','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/PlantsZombies.jpeg','Strategy','Plants vs. Zombies is a 2009 tower defense video game developed and published by PopCap Games. First released for Windows and Mac OS X, the game has since been ported to consoles, handhelds, and mobile devices. The player takes the role of a homeowner amid a zombie apocalypse. As a horde of zombies approaches along several parallel lanes, the player must defend the home by putting down plants, which fire projectiles at the zombies or otherwise detrimentally affect them. The player collects a currency called sun to buy plants. If a zombie makes it to the house on any lane, the player loses and must restart the level.',8,1),(7,'Farming Simulator 19','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Farming.jpeg','Simulation','The best-selling franchise takes a giant leap forward with a complete overhaul of the graphics engine, offering the most striking and immersive visuals and effects, along with the deepest and most complete farming experience ever.',9,1),(8,'Stardew Valley','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Stardew%20Valley.jpeg','Simulation','You\'ve inherited your grandfather\'s old farm plot in Stardew Valley. Armed with hand-me-down tools and a few coins, you set out to begin your new life. Can you learn to live off the land and turn these overgrown fields into a thriving home? It won\'t be easy. Ever since Joja Corporation came to town, the old ways of life have all but disappeared. The community center, once the town\'s most vibrant hub of activity, now lies in shambles. But the valley seems full of opportunity. With a little dedication, you might just be the one to restore Stardew Valley to greatness!',0,0),(9,'FIFA 2022','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/FIFA.jpeg','Sport Game','FIFA, also known as FIFA Football and set to be known as EA Sports FC from 2023, is a series of association football video games developed and released annually by Electronic Arts under the EA Sports label. As of 2011, the FIFA franchise has been localised into 18 languages and available in 51 countries. Listed in Guinness World Records as the best-selling sports video game franchise in the world, the FIFA series has sold over 325 million copies as of 2021. On 10 May 2022, it was announced that EA and FIFA\'s partnership is set to come to an end after 30 years from 12 July 2023 onwards; the series will be retitled EA Sports FC. FIFA intends to enter a partnership with a new developer to produce \"the real game that has the FIFA name\".',8,1),(10,'NBA 2K22','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/2K22.jpeg','Sport Game','NBA 2K22 is a 2021 basketball video game developed by Visual Concepts and published by 2K Sports, based on the National Basketball Association (NBA). It is the 23rd installment in the NBA 2K franchise, the successor to NBA 2K21 and the predecessor to NBA 2K23. The game was released on September 10, 2021 for Microsoft Windows, Nintendo Switch, PlayStation 4, PlayStation 5, Xbox One, and Xbox Series X/S. The NBA 2K22 Arcade Edition was released for Apple Arcade on October 19, 2021. As of May 2022, the game has sold over 10 million copies.',8,1),(11,'Portal 2','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Portal2.jpeg','Puzzle Game','Like the original Portal (2007), players solve puzzles by placing portals and teleporting between them. Portal 2 adds features including tractor beams, lasers, light bridges, and paint-like gels that alter player movement or allow portals to be placed on any surface. In the single-player campaign, players control Chell, who navigates the dilapidated Aperture Science Enrichment Center during its reconstruction by the supercomputer GLaDOS (Ellen McLain); new characters include robot Wheatley (Stephen Merchant) and Aperture founder Cave Johnson (J. K. Simmons). In the new cooperative mode, players solve puzzles together as robots Atlas and P-Body (both voiced by Dee Bradley Baker). Jonathan Coulton and the National produced songs for the game.',8,1),(12,'Monument Valley','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/MonumentValley.jpeg','Puzzle Game','Monument Valley is an indie puzzle game developed and published by Ustwo Games. The player leads the princess Ida through mazes of optical illusions and impossible objects while manipulating the world around her to reach various platforms. Monument Valley was developed over ten months beginning in early 2013 based on concept drawings by company artist Ken Wong. Its visual style was inspired by Japanese prints, minimalist sculpture, and indie games Windosill, Fez, and Sword & Sworcery, and was compared by critics to M. C. Escher drawings and Echochrome. The art was designed such that each frame would be worthy of public display. After a closed beta test, it was released for iOS on April 3, 2014, and was later ported to Android and Windows Phone. The game received generally favorable reviews. Critics praised its art and sound design, but noted its lack of difficulty and short length. ',0,0),(13,'Monster Hunter','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/MonsterHunter.jpeg','Acton','The games are primarily action role-playing games. The player takes the role of a Hunter, slaying or trapping large monsters across various landscapes as part of quests given to them by locals, with some quests involving the gathering of a certain item or items, which may put the Hunter at risk of facing various monsters. As part of its core gameplay loop, players use loot gained from slaying monsters, gathering resources, and quest rewards to craft improved weapons, armor, and other items that allow them to face more powerful monsters. All main series titles feature multiplayer (usually up to four players cooperatively), but can also be played single player.',0,0),(14,'Sekiro: Shadows Die Twice','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Sekiro.jpeg','Acton','The gameplay is focused on stealth, exploration, and combat, with a particular emphasis on boss battles. The game takes place in a fictionalized Japan during the Sengoku period and makes strong references to Buddhist mythology and philosophy. While making the game, lead director Hidetaka Miyazaki wanted to create a new intellectual property (IP) that marked a departure from the Dark Souls series of games also made by FromSoftware. The developers looked to games such as the Tenchu series for inspiration.',0,0),(15,'Final Fantasy 14','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/FinalFantasy.jpeg','Adventure','Final Fantasy XIV takes place in the fictional land of Eorzea, five years after the events of the original 2010 release. At the conclusion of the original game, the primal dragon Bahamut escapes from its lunar prison to initiate the Seventh Umbral Calamity, an apocalyptic event which destroys much of Eorzea. Through the gods\' blessing, the player character escapes the devastation by time traveling five years into the future. As Eorzea recovers and rebuilds, the player must deal with the impending threat of invasion by the Garlean Empire from the north.',8,1),(16,'Red Dead Redemption 2','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/RedDead.jpeg','Adventure','The game is played from a third-person perspective. The player may freely roam in its interactive open world, a fictionalized version of the Western United States and Northern Mexico, primarily by horseback and on foot. Gunfights emphasize a gunslinger gameplay mechanic called \"Dead Eye\" that allows players to mark multiple shooting targets on enemies in slow motion. The game makes use of a morality system, by which the player\'s actions in the game affect their character\'s levels of honor and fame and how other characters respond to the player. An online multiplayer mode is included with the game, allowing up to 16 players to engage in both cooperative and competitive gameplay in a recreation of the single-player setting.',3,1),(17,'Minecraft','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/MineCraft.jpeg','Sandbox','Prepare for an adventure of limitless possibilities as you build, mine, battle mobs, and explore the ever-changing Minecraft landscape.',8,1),(18,'Ark: Survival Evolved','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/ARK.jpeg','Sandbox','Stranded on the shores of a mysterious island, you must learn to survive. Use your cunning to kill or tame the primeval creatures roaming the land, and encounter other players to survive, dominate... and escape!',7,1),(19,'Cyberpunk 2077','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Cyberpunk.jpeg','Open-World','Cyberpunk 2077 is an open-world, action-adventure RPG set in the dark future of Night City — a dangerous megalopolis obsessed with power, glamor, and ceaseless body modification.',6,1),(20,'The Legend of Zelda: Breath of the Wild','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Zelda.jpeg','Open-World','Step into a world of discovery, exploration, and adventure in The Legend of Zelda: Breath of the Wild, a boundary-breaking new game in the acclaimed series.',0,0),(21,'Dark Soul','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/DS1.jpeg','Action','The games take place within a dark, medieval fantasy setting, where the player\'s character fights against knights, dragons, phantoms, demons, and other monstrous or supernatural entities.',9,2),(22,'Dark Soul2','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/DS2.jpeg','Action','DARK SOULS™ II: Scholar of the First Sin brings the franchise’s renowned obscurity & gripping gameplay to a new level. Join the dark journey and experience overwhelming enemy encounters, diabolical hazards, and unrelenting challenge.',5,1),(23,'Dark Soul3','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/DS3.jpeg','Action','Dark Souls III is an action role-playing game played in a third-person perspective. ',9,1),(24,'Elden Ring','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/EldenRing.jpeg','Action','Elden Ring is an action role-playing game played in a third person perspective, with gameplay focusing on combat and exploration. ',8,2),(25,'P5S','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/P5S.jpeg','Action','Persona 5 Strikers is a gameplay crossover between Koei Tecmo\'s hack and slash Dynasty Warriors series, and Atlus\' role-playing game Persona series. ',7,1),(26,'NieR:Automata','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Nier.jpeg','Action','NieR: Automata tells the story of androids 2B, 9S and A2 and their battle to reclaim the machine-driven dystopia overrun by powerful machines.',8,1),(27,'Metro Exodus','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Metro.jpeg','Action','Metro Exodus is a first-person shooter game with survival horror and stealth elements.',9,1),(28,'Assassin\'s Creed Odyssey','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Assassin.jpeg','Action','Assassin\'s Creed Odyssey is an action role-playing video game played from a third-person perspective. ',1,1);
/*!40000 ALTER TABLE `game_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_table`
--

DROP TABLE IF EXISTS `product_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_table` (
  `product_id` mediumint NOT NULL,
  `productname` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `productprice` int NOT NULL,
  `product_pic` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_table`
--

LOCK TABLES `product_table` WRITE;
/*!40000 ALTER TABLE `product_table` DISABLE KEYS */;
INSERT INTO `product_table` VALUES (1,'Minecraft Key',15000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/MineCraft.jpeg'),(2,'Cyberpunk 2077 Key',30000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Cyberpunk.jpeg'),(3,'Portal 2 Key',15000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Portal2.jpeg'),(4,'Plants vs. Zombies Key',10000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/PlantsZombies.jpeg'),(5,'P5S Key',35000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/P5S.jpeg'),(6,'Monument Valley Key',15000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/MonumentValley.jpeg'),(7,'The Witcher 3 Key',30000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/TheWitcher.jpeg'),(8,'Assassin\'s Creed Odyssey Key',20000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Assassin.jpeg'),(9,'Farming Simulator 19 Key',12000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Farming.jpeg'),(10,'Stardew Valley Key',20000,'https://gameview6206.oss-ap-southeast-1.aliyuncs.com/GamePics/Stardew%20Valley.jpeg');
/*!40000 ALTER TABLE `product_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_table`
--

DROP TABLE IF EXISTS `profile_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile_table` (
  `user_id` mediumint NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `facepicture` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `phonenumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dob` date NOT NULL,
  `gender` tinyint NOT NULL,
  `coin` int NOT NULL DEFAULT '0',
  `bookmark_list` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `token` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `reset_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `like_list` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_table`
--

LOCK TABLES `profile_table` WRITE;
/*!40000 ALTER TABLE `profile_table` DISABLE KEYS */;
INSERT INTO `profile_table` VALUES (1,'YinXueyang','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL,'12345678','xueyangyin@163.com','1999-05-18',1,200,'[]',NULL,NULL,'2018-09-23 13:28:02','[5,1,8,27]'),(2,'ChenHongjin','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL,'24537854','15308000713@163.com','2000-07-13',0,400,'[]',NULL,NULL,'2018-12-24 18:27:12','[8,2,39,27,5,1,10]'),(3,'ZhangXinlei','7c222fb2927d828af22f592134e8932480637c0d','https://gameview6206.oss-ap-southeast-1.aliyuncs.com/user/Cool_ICON2.jpg','15478722','matthewzxl@outlook.com','2001-01-28',2,5054,'[6,8,1,12,10]','cBtEkv6XposX_7V-3WiuAKYCSuroSR5W','321326','2018-07-22 22:29:24','[5,2,1,10,7,9,12]'),(4,'WangYongzhi','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL,'15475147','w627661598@gmail.com','1999-10-25',1,400,'[]','6f8kSWjvBDR7nu9lchiDlO1pY1rZj4i2','171816','2018-10-20 11:29:14','[5,1,6,4,2,7,9,10]'),(5,'Poisonous','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL,'15471548','iamtoxic@gmail.com','2000-12-12',1,150,'[]',NULL,'','2018-12-23 18:28:20','[5,2]'),(6,'Red','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL,'15478456','iamred@gmail.com','1990-11-12',0,344,'[1]','GH7WLvIHhP5pDbPz_WVGrClhRE42MVo3','711979','2018-12-24 14:27:02','[5,1,4,7,9]'),(7,'Flower','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL,'78489545','Iamblossom@gmail.com','2001-06-12',2,200,'[]',NULL,'','2018-09-24 04:27:57','[1,8,5,2]'),(8,'soFarsoGood','7c4a8d09ca3762af61e59520943dc26494f8941b',NULL,'15478782','iamfiamzero@gmail.com','2000-12-14',1,250,'[]','uIiUXDe4pxS1NXg9tET5JCV9Q_E_7fY0',NULL,'2018-05-24 04:57:21','[5,1,8,6,27]');
/*!40000 ALTER TABLE `profile_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply_table`
--

DROP TABLE IF EXISTS `reply_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply_table` (
  `reply_id` mediumint NOT NULL,
  `post_id` mediumint NOT NULL,
  `user_id` mediumint NOT NULL,
  `createtime` datetime NOT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`reply_id`) USING BTREE,
  KEY `post_id_idx1` (`post_id`) USING BTREE,
  KEY `user_id_idx1` (`user_id`) USING BTREE,
  CONSTRAINT `post_id2` FOREIGN KEY (`post_id`) REFERENCES `comment_table` (`post_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_id2` FOREIGN KEY (`user_id`) REFERENCES `profile_table` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply_table`
--

LOCK TABLES `reply_table` WRITE;
/*!40000 ALTER TABLE `reply_table` DISABLE KEYS */;
INSERT INTO `reply_table` VALUES (1,11,5,'2022-10-14 00:00:00','good!!'),(2,12,6,'2022-10-15 00:00:01','wow'),(3,12,6,'2022-10-15 00:00:02','agreeeee'),(4,12,6,'2022-10-15 00:00:03','wowowow'),(5,9,4,'2022-10-15 00:31:17','noooooo'),(6,4,1,'2022-10-09 13:23:55','OHHHHHHHHHHH'),(8,4,2,'2022-10-01 13:24:48','InTErEstING'),(9,1,2,'2022-10-03 19:25:51','lolllll'),(10,11,3,'2022-09-23 13:28:02','TAT'),(11,4,5,'2022-10-02 18:31:13','NOOOOOOO'),(12,4,1,'2022-10-06 18:31:50','i cant believe'),(13,9,3,'2022-10-22 07:03:54','I agree with you!!'),(14,9,3,'2022-10-22 12:01:34','aGREE YOU');
/*!40000 ALTER TABLE `reply_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scoring_table`
--

DROP TABLE IF EXISTS `scoring_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scoring_table` (
  `scoring_id` mediumint NOT NULL,
  `game_id` mediumint NOT NULL,
  `user_id` mediumint NOT NULL,
  `score` int NOT NULL,
  `reasons_for_scoring` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`scoring_id`) USING BTREE,
  KEY `user_id_idx` (`user_id`) USING BTREE,
  KEY `game_id_idx` (`game_id`) USING BTREE,
  CONSTRAINT `game_id3` FOREIGN KEY (`game_id`) REFERENCES `game_table` (`game_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_id3` FOREIGN KEY (`user_id`) REFERENCES `profile_table` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scoring_table`
--

LOCK TABLES `scoring_table` WRITE;
/*!40000 ALTER TABLE `scoring_table` DISABLE KEYS */;
INSERT INTO `scoring_table` VALUES (1,2,6,8,'warn people about fuck-ups, greed,','2022-10-21 01:45:22'),(2,24,5,6,'never respond to the chazz again','2022-10-21 02:27:23'),(3,2,5,6,'Ever since Dark Souls got popular, many games have tried to emulate the satisfying gameplay the franchise provides. Few manage to do so, but there have been successes. Nioh 2 is one of them.','2022-10-21 02:28:13'),(4,5,5,8,'Got severely obliterated as advertised, not disappointed in that field. ','2022-10-21 01:33:16'),(5,4,5,8,'What I didn\'t expect was the 100  hour campaign, with about 80 bosses and a billion ways to defeat them.','2022-10-21 01:34:56'),(6,7,5,9,'Let me put it this way: Nioh 2 is like endurance training at the gym.','2022-10-21 01:36:30'),(7,6,5,8,'And if you beat that final boss, the absolute bringer of nightmares that has awaited you for the entire game, it\'ll feel like the weight of a planet has been lifted off your shoulders.','2022-10-21 01:38:37'),(8,9,6,8,'warn people about fuck-ups, greed,','2022-10-21 01:45:22'),(9,10,6,8,'warn people about fuck-ups, greed,','2022-10-21 01:45:22'),(10,11,6,8,'I only respect the Review Bombs until there\'s finally better ways to warn people about fuck-ups, greed, ambitious and predatory systems, or forced injected/biased politics.','2022-10-21 01:57:55'),(11,15,6,8,'very nice','2022-10-21 02:16:46'),(12,16,6,3,'You feel powerful. But you aren’t.','2022-10-21 02:29:29'),(13,17,6,8,'Do you like Souls-likes? - Do you like creative enemy-design?','2022-10-21 02:24:29'),(14,18,5,7,'Do you like creative enemy-design?','2022-10-21 02:26:45'),(15,19,5,6,'but I get why someone might vote it down.','2022-10-21 02:26:59'),(16,24,3,10,'gOOD','2022-10-22 05:07:39');
/*!40000 ALTER TABLE `scoring_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-03 13:36:54
