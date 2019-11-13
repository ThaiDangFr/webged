SET FOREIGN_KEY_CHECKS=0;

-- MySQL dump 9.10
--
-- Host: localhost    Database: dang
-- ------------------------------------------------------
-- Server version	4.0.18-standard


-- REFERENCE :
-- http://www.mysql.com/documentation/mysql/bychapter/manual_Column_types.html#Column_types


-- EFFACER LES FILS D'ABORD (à cause des foreign key)
-- la relation étant "dépend de"


DROP TABLE IF EXISTS Trace;
--
DROP TABLE IF EXISTS MailArchiveAttach;
DROP TABLE IF EXISTS MailArchive;
--
DROP TABLE IF EXISTS GedState;
DROP TABLE IF EXISTS GedNotification;
DROP TABLE IF EXISTS GedTrash;
DROP TABLE IF EXISTS GedWorkflowProc;
DROP TABLE IF EXISTS GedFilesVersion;
DROP TABLE IF EXISTS GedFilesDescr;
DROP TABLE IF EXISTS GedFilesComment;
DROP TABLE IF EXISTS GedFiles;
DROP TABLE IF EXISTS GedDirThesaurus;
DROP TABLE IF EXISTS GedDirectoryRights;
DROP TABLE IF EXISTS GedDirectory;
DROP TABLE IF EXISTS ThesaurusItem;
DROP TABLE IF EXISTS Thesaurus;
DROP TABLE IF EXISTS GedTemplateItem;
DROP TABLE IF EXISTS GedTemplate;
DROP TABLE IF EXISTS GedWorkflowStep;
DROP TABLE IF EXISTS GedWorkflow;
DROP TABLE IF EXISTS GedPref;
--
DROP TABLE IF EXISTS ArticleLink;
DROP TABLE IF EXISTS ArticleFile;
DROP TABLE IF EXISTS ArticleComment;
DROP TABLE IF EXISTS ArticleModerator;
DROP TABLE IF EXISTS Article;
DROP TABLE IF EXISTS ArticleCategory;
DROP TABLE IF EXISTS ArticlePref;
--
DROP TABLE IF EXISTS CmsInboxFriends;
DROP TABLE IF EXISTS CmsInboxAttachment;
DROP TABLE IF EXISTS CmsInbox;
DROP TABLE IF EXISTS CmsPreferences;
DROP TABLE IF EXISTS CmsBlocks;
DROP TABLE IF EXISTS CmsModuleAuthorization;
DROP TABLE IF EXISTS CmsBLockVisibility;
DROP TABLE IF EXISTS CmsModule;
--
DROP TABLE IF EXISTS StyleSheet;
--
DROP TABLE IF EXISTS SnmpAlert;
DROP TABLE IF EXISTS SnmpAlertUser;
DROP TABLE IF EXISTS SnmpAction;
DROP TABLE IF EXISTS SnmpValueData;
DROP TABLE IF EXISTS SnmpGFValueData;
DROP TABLE IF EXISTS SnmpGFValue;
DROP TABLE IF EXISTS SnmpGraphicFormula;
DROP TABLE IF EXISTS SnmpFormula;
DROP TABLE IF EXISTS SnmpReport;
DROP TABLE IF EXISTS SnmpValue;
DROP TABLE IF EXISTS SnmpParam;
DROP TABLE IF EXISTS SnmpHost;
DROP TABLE IF EXISTS SnmpOID;
DROP TABLE IF EXISTS SnmpMibFile;
--
DROP TABLE IF EXISTS License;
--
DROP TABLE IF EXISTS ConversionCache;
DROP TABLE IF EXISTS FileBaseCache;
DROP TABLE IF EXISTS FileBase;
--
DROP TABLE IF EXISTS UserGroups;
DROP TABLE IF EXISTS Groups;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Terminal;
DROP TABLE IF EXISTS Image;
DROP TABLE IF EXISTS MimeTypes;
--
DROP TABLE IF EXISTS TreeMenu;
--
DROP TABLE IF EXISTS Sms;
--


