-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: dialer
-- ------------------------------------------------------
-- Server version	8.0.22-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `DM Contacts`
--

DROP TABLE IF EXISTS `DM Contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DM Contacts` (
  `Sr. no` int NOT NULL,
  `Company Name` varchar(200) DEFAULT NULL,
  `Company Address` varchar(500) DEFAULT NULL,
  `Website` varchar(200) DEFAULT NULL,
  `Contact` int DEFAULT NULL,
  `Email ID` varchar(35) DEFAULT NULL,
  `City` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Sr. no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DM Contacts`
--

LOCK TABLES `DM Contacts` WRITE;
/*!40000 ALTER TABLE `DM Contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `DM Contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent_history`
--

DROP TABLE IF EXISTS `agent_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent_history` (
  `agent_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `logout_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent_history`
--

LOCK TABLES `agent_history` WRITE;
/*!40000 ALTER TABLE `agent_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `agent_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent_status_master`
--

DROP TABLE IF EXISTS `agent_status_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent_status_master` (
  `status_id` int NOT NULL,
  `value` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent_status_master`
--

LOCK TABLES `agent_status_master` WRITE;
/*!40000 ALTER TABLE `agent_status_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `agent_status_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent_strategy_master`
--

DROP TABLE IF EXISTS `agent_strategy_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent_strategy_master` (
  `strategy_id` int NOT NULL,
  `value` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent_strategy_master`
--

LOCK TABLES `agent_strategy_master` WRITE;
/*!40000 ALTER TABLE `agent_strategy_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `agent_strategy_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent_timings`
--

DROP TABLE IF EXISTS `agent_timings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent_timings` (
  `agent_id` bigint NOT NULL,
  `day` varchar(10) NOT NULL,
  `start_time` time NOT NULL DEFAULT '09:00:00',
  `end_time` time NOT NULL DEFAULT '18:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent_timings`
--

LOCK TABLES `agent_timings` WRITE;
/*!40000 ALTER TABLE `agent_timings` DISABLE KEYS */;
/*!40000 ALTER TABLE `agent_timings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agents`
--

DROP TABLE IF EXISTS `agents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agents` (
  `agent_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `country_code` int NOT NULL,
  `phone_no` bigint NOT NULL,
  `agent_email` varchar(50) DEFAULT NULL,
  `dept` varchar(80) DEFAULT NULL,
  `language_code` varchar(10) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `last_allocated` datetime NOT NULL,
  `agent_name` varchar(50) DEFAULT NULL,
  `parent` bigint DEFAULT NULL,
  PRIMARY KEY (`agent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agents`
--

LOCK TABLES `agents` WRITE;
/*!40000 ALTER TABLE `agents` DISABLE KEYS */;
/*!40000 ALTER TABLE `agents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attempts`
--

DROP TABLE IF EXISTS `attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attempts` (
  `attempt_id` int NOT NULL AUTO_INCREMENT,
  `call_sid` varchar(256) DEFAULT NULL,
  `uuid` varchar(256) DEFAULT NULL,
  `lead_id` int NOT NULL DEFAULT '0',
  `agent_init_time` datetime DEFAULT NULL,
  `agent_ring_time` datetime DEFAULT NULL,
  `agent_ans_time` datetime DEFAULT NULL,
  `agent_end_time` datetime DEFAULT NULL,
  `lead_init_time` datetime DEFAULT NULL,
  `lead_ring_time` datetime DEFAULT NULL,
  `lead_ans_time` datetime DEFAULT NULL,
  `lead_end_time` datetime DEFAULT NULL,
  `call_patch_time` datetime DEFAULT NULL,
  `agent_ring_duration` int DEFAULT '0',
  `agent_talk_duration` int DEFAULT '0',
  `lead_ring_duration` int DEFAULT '0',
  `lead_talk_duration` int DEFAULT '0',
  `hangup_by` varchar(12) DEFAULT '0',
  `agent_hangup_cause` int DEFAULT '0',
  `lead_hangup_cause` int DEFAULT '0',
  `user_id` bigint DEFAULT NULL,
  `lead_no` bigint DEFAULT NULL,
  `agent_no` bigint DEFAULT NULL,
  `agent_call_status` tinyint DEFAULT '0',
  `lead_call_status` tinyint DEFAULT '0',
  `unlock_status` int DEFAULT NULL,
  `remarks` text,
  PRIMARY KEY (`attempt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attempts`
--

LOCK TABLES `attempts` WRITE;
/*!40000 ALTER TABLE `attempts` DISABLE KEYS */;
INSERT INTO `attempts` VALUES (1,'101','102',50021,'2020-12-03 00:34:05','2020-12-03 00:34:05','2020-12-03 00:34:05','2020-12-03 00:34:05','2020-12-03 00:34:05','2020-12-03 00:34:05','2020-12-03 00:34:05','2020-12-03 00:34:05','2020-12-03 00:34:05',10,20,30,0,'10',22,33,50021,9066074791,8908179047,1,0,35,'test'),(2,'105','103',50021,'2020-12-03 02:06:19','2020-12-03 02:06:19','2020-12-03 02:06:19','2020-12-03 02:06:19','2020-12-03 02:06:19','2020-12-03 02:06:19','2020-12-03 02:06:19','2020-12-03 02:06:19','2020-12-03 02:06:19',10,20,30,0,'10',22,33,50021,6565666565,9989898899,1,0,35,'test');
/*!40000 ALTER TABLE `attempts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base`
--

DROP TABLE IF EXISTS `base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `base` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mobile` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `task_id` bigint DEFAULT NULL,
  `status` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `base_FK` (`task_id`),
  KEY `base_FK_1` (`user_id`),
  CONSTRAINT `base_FK` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`task_id`),
  CONSTRAINT `base_FK_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base`
--

LOCK TABLES `base` WRITE;
/*!40000 ALTER TABLE `base` DISABLE KEYS */;
INSERT INTO `base` VALUES (1,9066074791,50021,9,0),(2,8908179047,50021,9,0),(3,9066074791,50021,10,0),(4,8908179047,50021,10,0),(5,5546468468,50021,10,0),(6,9066074791,50021,11,0),(7,8908179047,50021,11,0),(8,5546468468,50021,11,0),(9,9066074791,50021,12,0),(10,8908179047,50021,12,0),(11,9066074791,50021,13,0),(12,8908179047,50021,13,0),(13,9066074791,50021,14,0),(14,8908179047,50021,14,0),(15,9066074791,50021,15,0),(16,8908179047,50021,15,0),(17,5546468468,50021,15,0),(18,9066074791,50021,16,0),(19,8908179047,50021,16,0),(20,5546468468,50021,16,0),(21,9066074791,50021,17,0),(22,8908179047,50021,17,0);
/*!40000 ALTER TABLE `base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `billing_logs`
--

DROP TABLE IF EXISTS `billing_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `billing_logs` (
  `attempt_uid` varchar(128) NOT NULL,
  `credits` int NOT NULL,
  `res_time` datetime NOT NULL,
  `user_id` bigint NOT NULL,
  `ded_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billing_logs`
--

LOCK TABLES `billing_logs` WRITE;
/*!40000 ALTER TABLE `billing_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `billing_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `call_disposition`
--

DROP TABLE IF EXISTS `call_disposition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `call_disposition` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `mobile` bigint DEFAULT NULL,
  `remarks` text,
  `req_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `call_disposition`
--

LOCK TABLES `call_disposition` WRITE;
/*!40000 ALTER TABLE `call_disposition` DISABLE KEYS */;
/*!40000 ALTER TABLE `call_disposition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `call_status_master`
--

DROP TABLE IF EXISTS `call_status_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `call_status_master` (
  `status_id` int NOT NULL,
  `value` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `call_status_master`
--

LOCK TABLES `call_status_master` WRITE;
/*!40000 ALTER TABLE `call_status_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `call_status_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cli`
--

DROP TABLE IF EXISTS `cli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cli` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `cli` varchar(15) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `req_date` datetime DEFAULT NULL,
  `msisdn` bigint DEFAULT NULL,
  `otp` int DEFAULT NULL,
  `is_verify` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cli`
--

LOCK TABLES `cli` WRITE;
/*!40000 ALTER TABLE `cli` DISABLE KEYS */;
/*!40000 ALTER TABLE `cli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_history`
--

DROP TABLE IF EXISTS `client_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event` enum('call','sms','email') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `data` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `mobile` bigint DEFAULT NULL,
  `email_id` varchar(50) DEFAULT NULL,
  `req_date` datetime DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `agent_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_history`
--

LOCK TABLES `client_history` WRITE;
/*!40000 ALTER TABLE `client_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `client_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mobile` bigint DEFAULT NULL,
  `email_id` varchar(100) DEFAULT NULL,
  `company_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `company_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `city` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `company_website` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `category` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country_codes`
--

DROP TABLE IF EXISTS `country_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country_codes` (
  `calling_code` int NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `country_code` varchar(2) DEFAULT NULL,
  `credits` int DEFAULT NULL,
  `mobile` int DEFAULT NULL,
  PRIMARY KEY (`calling_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country_codes`
--

LOCK TABLES `country_codes` WRITE;
/*!40000 ALTER TABLE `country_codes` DISABLE KEYS */;
/*!40000 ALTER TABLE `country_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country_details`
--

DROP TABLE IF EXISTS `country_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country_details` (
  `calling_code` int unsigned NOT NULL,
  `country_code` char(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `mobile` int unsigned NOT NULL,
  `credits` int unsigned NOT NULL DEFAULT '3',
  `credits_price` int DEFAULT '1',
  PRIMARY KEY (`calling_code`),
  KEY `territory_idx` (`country_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country_details`
--

LOCK TABLES `country_details` WRITE;
/*!40000 ALTER TABLE `country_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `country_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credits_log`
--

DROP TABLE IF EXISTS `credits_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credits_log` (
  `req_date` datetime DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `parent` bigint DEFAULT NULL,
  `parent_credits` float DEFAULT NULL,
  `credits_deducted` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credits_log`
--

LOCK TABLES `credits_log` WRITE;
/*!40000 ALTER TABLE `credits_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `credits_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dids`
--

DROP TABLE IF EXISTS `dids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dids` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `did` bigint DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dids`
--

LOCK TABLES `dids` WRITE;
/*!40000 ALTER TABLE `dids` DISABLE KEYS */;
/*!40000 ALTER TABLE `dids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dnc`
--

DROP TABLE IF EXISTS `dnc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dnc` (
  `dnc_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(60) DEFAULT NULL,
  `mobile` varchar(30) DEFAULT NULL,
  `flag` tinyint DEFAULT '0',
  `user_id` bigint DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(100) DEFAULT NULL,
  `company_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`dnc_id`),
  KEY `dnc_FK` (`user_id`),
  CONSTRAINT `dnc_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dnc`
--

LOCK TABLES `dnc` WRITE;
/*!40000 ALTER TABLE `dnc` DISABLE KEYS */;
INSERT INTO `dnc` VALUES (2,'vikkeesingh@hotmail.com','8908179047',0,50021,'2020-12-04 01:23:29',NULL,NULL),(3,'vikkeesingh@gmail.com','9066074792',0,50021,'2020-12-04 03:28:23','',''),(7,'vikkeesingh@gmail.com','09066074791',0,50024,'2020-12-05 01:34:54','',''),(8,'vikkeesingh30@gmail.com','23424393423',0,50026,'2020-12-05 01:53:16','',''),(10,'hello@gmail.com','4349759334',0,50021,'2020-12-06 17:21:52','Just for test','');
/*!40000 ALTER TABLE `dnc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leads`
--

DROP TABLE IF EXISTS `leads`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leads` (
  `lead_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `country_code` int DEFAULT NULL,
  `lead_no` bigint NOT NULL,
  `agent_no` bigint DEFAULT NULL,
  `req_time` datetime NOT NULL,
  `schedule_time` datetime NOT NULL,
  `attempt_time` datetime DEFAULT NULL,
  `init_time` datetime DEFAULT NULL,
  `cli` bigint DEFAULT NULL,
  `call_uid` varchar(256) DEFAULT NULL,
  `agent_status` int DEFAULT NULL,
  `lead_status` int DEFAULT NULL,
  `status` int NOT NULL DEFAULT '0',
  `attempt_count` int NOT NULL DEFAULT '0',
  `pick_id` int NOT NULL DEFAULT '0',
  `type` varchar(20) DEFAULT NULL,
  `retry_status` varchar(50) DEFAULT NULL,
  `template_id` int DEFAULT NULL,
  PRIMARY KEY (`lead_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leads`
--

LOCK TABLES `leads` WRITE;
/*!40000 ALTER TABLE `leads` DISABLE KEYS */;
/*!40000 ALTER TABLE `leads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mt_sms`
--

DROP TABLE IF EXISTS `mt_sms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mt_sms` (
  `trans_id` bigint NOT NULL AUTO_INCREMENT,
  `msisdn` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `sms_text` varchar(500) DEFAULT NULL,
  `req_date` datetime DEFAULT NULL,
  `shortcode` varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `sms_type` int DEFAULT NULL,
  `campaign_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `sms_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`trans_id`),
  KEY `status_index` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mt_sms`
--

LOCK TABLES `mt_sms` WRITE;
/*!40000 ALTER TABLE `mt_sms` DISABLE KEYS */;
/*!40000 ALTER TABLE `mt_sms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `msisdn` bigint DEFAULT NULL,
  `otp` int DEFAULT NULL,
  `otp_time` datetime DEFAULT NULL,
  `is_verify` varchar(5) DEFAULT NULL COMMENT 'NO/YES',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3779 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp`
--

LOCK TABLES `otp` WRITE;
/*!40000 ALTER TABLE `otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `remarks_master`
--

DROP TABLE IF EXISTS `remarks_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `remarks_master` (
  `remarks` varchar(50) DEFAULT NULL,
  `value` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remarks_master`
--

LOCK TABLES `remarks_master` WRITE;
/*!40000 ALTER TABLE `remarks_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `remarks_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserved_credits`
--

DROP TABLE IF EXISTS `reserved_credits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserved_credits` (
  `attempt_uid` varchar(128) NOT NULL,
  `credits` int NOT NULL,
  `res_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`attempt_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserved_credits`
--

LOCK TABLES `reserved_credits` WRITE;
/*!40000 ALTER TABLE `reserved_credits` DISABLE KEYS */;
/*!40000 ALTER TABLE `reserved_credits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `retry_log`
--

DROP TABLE IF EXISTS `retry_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `retry_log` (
  `retry_time` datetime DEFAULT NULL,
  `lead_id` bigint DEFAULT NULL,
  `call_sid` varchar(128) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `uu_id` varchar(128) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `widget_id` bigint DEFAULT NULL,
  `retry_attempt` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `retry_log`
--

LOCK TABLES `retry_log` WRITE;
/*!40000 ALTER TABLE `retry_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `retry_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `retry_settings`
--

DROP TABLE IF EXISTS `retry_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `retry_settings` (
  `widget_id` bigint DEFAULT NULL,
  `attempt_count` int DEFAULT NULL,
  `retry_interval` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `retry_settings`
--

LOCK TABLES `retry_settings` WRITE;
/*!40000 ALTER TABLE `retry_settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `retry_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sms_master`
--

DROP TABLE IF EXISTS `sms_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sms_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `smstext` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sms_master`
--

LOCK TABLES `sms_master` WRITE;
/*!40000 ALTER TABLE `sms_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `sms_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `task_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `status` tinyint DEFAULT '0',
  `remaining_call` bigint DEFAULT NULL,
  `total_call` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`),
  KEY `tasks_FK` (`user_id`),
  CONSTRAINT `tasks_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,'new task','adding new task description',0,100,200,50021,'2020-12-06 22:16:03'),(2,'other task','adding other task description',0,250,300,50021,'2020-12-06 22:17:08'),(7,'vikkee','description',NULL,NULL,NULL,50021,'2020-12-10 01:37:38'),(8,'vikkee','description',NULL,NULL,NULL,50021,'2020-12-10 01:37:56'),(9,'Vikkee Singh','kjhgiygi',NULL,NULL,NULL,50025,'2020-12-11 01:36:37'),(10,'zcsa','sdsdfsdfsddf',0,NULL,NULL,50024,'2020-12-11 01:46:22'),(11,'Sds','DSDSDCD',0,NULL,NULL,50026,'2020-12-11 01:51:39'),(12,'Vikkee Singh','kjhiyugigi',0,NULL,NULL,50025,'2020-12-11 01:56:26'),(13,'sdnaksd','dksjfknskvnk',0,NULL,NULL,50025,'2020-12-11 01:59:32'),(14,'sdlkfsld','wlnewnfod',0,NULL,NULL,50024,'2020-12-11 02:02:44'),(15,'sdc.mdlkas','sldncsldnl',0,NULL,NULL,50024,'2020-12-11 02:07:12'),(16,'Vikkee Singh','jhgutdydyd',0,NULL,NULL,50024,'2020-12-11 02:11:28'),(17,'Vikke','sdkhgvid',0,NULL,NULL,50025,'2020-12-11 02:12:55');
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_details`
--

DROP TABLE IF EXISTS `user_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `req_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `pincode` int DEFAULT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `credits_available` float DEFAULT '0',
  `credits_used` float DEFAULT '0',
  `unit_rate` float DEFAULT NULL,
  `parent` bigint DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `parent_company` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pulse_duration` int DEFAULT '60',
  `flag` tinyint DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `domain` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_userid` (`user_id`),
  CONSTRAINT `fk_userid` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_details`
--

LOCK TABLES `user_details` WRITE;
/*!40000 ALTER TABLE `user_details` DISABLE KEYS */;
INSERT INTO `user_details` VALUES (1,50016,'2020-11-23 21:37:46',NULL,NULL,'vikkeesingh@gmail.com','vikkee','9066074791','vikkeesingh@gmail.com',NULL,NULL,NULL,0,0,NULL,50016,'admin',NULL,60,NULL,NULL,NULL),(2,50021,'2020-11-23 21:38:28',NULL,NULL,'vikkeesingh@gmail.com','vikkee','9066074791','vikkeesingh@gmail.com',NULL,NULL,NULL,0,0,NULL,50016,'user',NULL,60,NULL,NULL,NULL),(3,50023,'2020-11-23 21:59:52',NULL,NULL,'vikkeesingh10@gmail.com','vikkee','767688768','vikkeesingh10@gmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL),(4,50024,'2020-11-23 23:02:53',NULL,NULL,'vikkeesingh11@gmail.com','vikkee singh','767688768','vikkeesingh11@gmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL),(5,50025,'2020-11-23 23:06:10',NULL,NULL,'vikkeesingh1@gmail.com','Vikkee Singh','8908179047','vikkeesingh1@gmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL),(6,50026,'2020-11-24 21:45:52',NULL,NULL,'vikkeesingh20@gmail.com','Vikkee Singh','8908179047','vikkeesingh20@gmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL),(7,50027,'2020-11-26 00:45:30',NULL,NULL,'vikkee.singh@hotmail.com','Hello','98349375345','vikkee.singh@hotmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL),(8,50028,'2020-11-27 20:10:03',NULL,NULL,'vikkeesingh@gmail.com','Vikkee Singh','9066074791','vikkeesingh@gmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL),(9,50029,'2020-11-27 22:47:36',NULL,NULL,'vikkeesingh50@gmail.com','Vikkee Singh','9066074791','vikkeesingh50@gmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL),(10,50030,'2020-11-27 23:00:14',NULL,NULL,'vikkeesingh51@gmail.com','Vikkee Singh','9066074791','vikkeesingh51@gmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL),(11,50031,'2020-11-27 23:02:22',NULL,NULL,'vikkeesingh52@gmail.com','Vikkee Singh','9066074791','vikkeesingh52@gmail.com',NULL,NULL,NULL,0,0,1,50021,NULL,NULL,60,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_details_log`
--

DROP TABLE IF EXISTS `user_details_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_details_log` (
  `id` bigint DEFAULT NULL,
  `user_id` varchar(50) DEFAULT NULL,
  `req_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `pincode` int DEFAULT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `credits_available` float DEFAULT '0',
  `credits_used` float DEFAULT '0',
  `unit_rate` float DEFAULT NULL,
  `parent` bigint DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `parent_company` varchar(50) DEFAULT NULL,
  `pulse_duration` int DEFAULT '30',
  `flag` tinyint DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `domain` varchar(100) DEFAULT NULL,
  `delete_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_details_log`
--

LOCK TABLES `user_details_log` WRITE;
/*!40000 ALTER TABLE `user_details_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_details_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_role_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `ROLE` varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_userid_role` (`ROLE`,`user_id`),
  KEY `fk_username_idx` (`user_id`),
  CONSTRAINT `fk_userid_roles` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'vikkeesingh@gmail.com',50016,'ROLE_ADMIN'),(2,'vikkeesingh@gmail.com',50021,'ROLE_ADMIN'),(3,'vikkeesingh10@gmail.com',50023,'ROLE_USER'),(4,'vikkeesingh11@gmail.com',50024,'ROLE_USER'),(5,'vikkeesingh1@gmail.com',50025,'ROLE_USER'),(6,'vikkeesingh20@gmail.com',50026,'ROLE_USER'),(7,'vikkee.singh@hotmail.com',50027,'ROLE_USER'),(8,'vikkeesingh@gmail.com',50028,'ROLE_USER'),(9,'vikkeesingh50@gmail.com',50029,'ROLE_USER'),(10,'vikkeesingh51@gmail.com',50030,'ROLE_USER'),(11,'vikkeesingh52@gmail.com',50031,'ROLE_USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles_log`
--

DROP TABLE IF EXISTS `user_roles_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles_log` (
  `user_role_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `ROLE` varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_userid_role` (`ROLE`,`user_id`),
  KEY `fk_username_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles_log`
--

LOCK TABLES `user_roles_log` WRITE;
/*!40000 ALTER TABLE `user_roles_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(256) NOT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  `parent` bigint DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50032 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (50016,'$2a$10$V1MMhWT/Uc5PGt0n9C33e.qk2qW67PxRJEI.AE4HOSiAIOe59pLZm',1,50016,NULL),(50021,'$2a$10$kAaQiUQb.LNMpCvpDVCPyulZLQvp9fQfNHgU1grO7bqRobg4W6fLO',1,50016,'vikkeesingh30@gmail.com'),(50022,'$2a$10$6VV2bx8ukMYz9eiYKdsdNu7.uvZPl5AHixCtXnAiKPKvcNQn4uv3C',1,NULL,NULL),(50023,'vikkee@90',1,50021,'vikkeesingh10@gmail.com'),(50024,'vikkee@91',1,50021,'vikkeesingh11@gmail.com'),(50025,'$2a$10$ceH..rjB8dPEk46c7KwcSOPaOEFvAR1xQ.Oy42UTa3Qtdr8ZsNhOi',1,50021,'vikkeesingh1@gmail.com'),(50026,'$2a$10$kAaQiUQb.LNMpCvpDVCPyulZLQvp9fQfNHgU1grO7bqRobg4W6fLO',1,50021,'vikkeesingh20@gmail.com'),(50027,'$2a$10$.pnOspTfv8NQAt7ddhyTbOKsdsbiQpQ0xliuforkKCZqpKPR8wBv2',1,50021,'vikkee.singh@hotmail.com'),(50028,'$2a$10$PvpIcP8NZ8ov5g/wbPRJou31iDPbduMvCMmWJjH.ZAMGg5tcgbqf2',1,50021,'vikkeesingh@gmail.com'),(50029,'$2a$10$4ZLq9gYmy2fY5jVlH6I9JeR0zOuCOLwcQ6cZ4VV3ICBl80O8fd83G',1,50021,'vikkeesingh50@gmail.com'),(50030,'$2a$10$Xq8rBMQ70/e6n9PgCR5V/e5kMRYmrXLPYbYKgmr6i5F1lkgeH8dAS',1,50021,'vikkeesingh51@gmail.com'),(50031,'$2a$10$Jop3Y9p3I92QfMgshOHNh.koKYgT15BfZD6RS52fL4iV4PiKV9Zxm',1,50021,'vikkeesingh52@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_log`
--

DROP TABLE IF EXISTS `users_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_log` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(256) NOT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  `parent` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=50007 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_log`
--

LOCK TABLES `users_log` WRITE;
/*!40000 ALTER TABLE `users_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-11 22:10:34