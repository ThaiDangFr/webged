SET FOREIGN_KEY_CHECKS=1;


-- =======================
-- CREER LES PERES D'ABORD
-- =======================
-- note :
-- binary signifie sensible à la casse





-- GENERAL BEGIN --
CREATE TABLE Sms (
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,	
	date		DATETIME NOT NULL,
	fromTel		VARCHAR(500) NOT NULL,
	toTel		VARCHAR(500) NOT NULL,
	err		BIGINT(20) UNSIGNED DEFAULT NULL,
	apimsgid	VARCHAR(500) DEFAULT NULL,
	status		BIGINT(20) UNSIGNED DEFAULT NULL,
	statusMsg	VARCHAR(500) NOT NULL,
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE TreeMenu (
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	menuId		BIGINT(20) UNSIGNED NOT NULL,
	name		VARCHAR(500) NOT NULL,
	parentId	BIGINT(20) UNSIGNED,
	href		VARCHAR(500),
	root		SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	PRIMARY KEY	(id),
	INDEX		(parentId),
	FOREIGN KEY	(parentId) REFERENCES TreeMenu(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE StyleSheet (
	cssId		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(500) NOT NULL,
	css		TEXT DEFAULT NULL,
	PRIMARY KEY  	(cssId)
) ENGINE=InnoDB;
--
CREATE TABLE License (
	licenseId   	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	licenseKey  	VARCHAR(3000) DEFAULT NULL,
	PRIMARY KEY 	(licenseId)
) ENGINE=InnoDB;
--
-- table MimeTypes insensible a la casse
-- c'est une table qui n'est pas relationné
CREATE TABLE MimeTypes (
	mimetypesId   BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	extension     VARCHAR(50) NOT NULL,
	primaryType   VARCHAR(50) NOT NULL,
	secondaryType VARCHAR(50) NOT NULL,
	PRIMARY KEY   (mimetypesId),
	UNIQUE KEY    (extension)
) ENGINE=InnoDB;
--
CREATE TABLE Image (
	imageId		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	width       	SMALLINT UNSIGNED NOT NULL,
	height      	SMALLINT UNSIGNED NOT NULL,
	extension   	VARCHAR(20) NOT NULL,
	PRIMARY KEY 	(imageId)
) ENGINE=InnoDB;
--
CREATE TABLE Terminal (
	terminalId  	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	userAgent   	VARCHAR(255) NOT NULL,
	navType     	ENUM('wap','imode','pc','pda') DEFAULT 'pc',
	counter     	BIGINT(20) UNSIGNED NOT NULL DEFAULT '0',
	imageId     	BIGINT(20) UNSIGNED DEFAULT NULL,
	PRIMARY KEY 	(terminalId),
	UNIQUE KEY  	(userAgent),
	INDEX       	(imageId),
	FOREIGN KEY 	(imageId) REFERENCES Image(imageId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE User (
	userId      	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	login       	VARCHAR(255) NOT NULL,
	password    	VARCHAR(500) binary NOT NULL,
  	firstName   	VARCHAR(500) binary DEFAULT NULL,
  	lastName    	VARCHAR(500) binary DEFAULT NULL,
  	mail        	VARCHAR(500) binary DEFAULT NULL,
  	officePhone   	VARCHAR(500) binary DEFAULT NULL,
  	mobilePhone 	VARCHAR(500) binary DEFAULT NULL,
  	icq         	VARCHAR(500) binary DEFAULT NULL,
  	jabber      	VARCHAR(500) binary DEFAULT NULL,
  	msn         	VARCHAR(500) binary DEFAULT NULL,
	expiryTime	DATETIME,
	fwdMsgToMail	SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	cpMsgToSMS	SMALLINT UNSIGNED NOT NULL DEFAULT '0',
  	PRIMARY KEY 	(userId),
  	UNIQUE KEY  	(login)
) ENGINE=InnoDB;
-- GENERAL END --






-- FILE BASE BEGIN --
CREATE TABLE FileBase (
	fileBaseId	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	filename	VARCHAR(3000) NOT NULL,
	application	VARCHAR(500),
	date		DATETIME NOT NULL,
	expired	DATETIME DEFAULT NULL,
	size		BIGINT(20) UNSIGNED,
	PRIMARY KEY	(fileBaseId)
) ENGINE=InnoDB;
--
CREATE TABLE FileBaseCache (
  	cacheId     	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	imageId		BIGINT(20) UNSIGNED NOT NULL,
	fileBase	BIGINT(20) UNSIGNED NOT NULL,
	cacheFile	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(cacheId),
	INDEX		(imageId),
	INDEX		(fileBase),
	INDEX		(cacheFile),
	FOREIGN	KEY	(imageId) REFERENCES Image(imageId) ON DELETE CASCADE,
	FOREIGN	KEY	(fileBase) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE,
	FOREIGN	KEY	(cacheFile) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE ConversionCache (
	id			BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	extension		VARCHAR(500) NOT NULL,
	param			VARCHAR(500),
	orifile			BIGINT(20) UNSIGNED NOT NULL,
	cachefile		BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY		(id),
	INDEX			(orifile),
	INDEX			(cachefile),
	FOREIGN KEY		(orifile) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE,
	FOREIGN KEY		(cachefile) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE
) ENGINE=InnoDB;


-- FILE BASE END --







-- CMS TABLE BEGIN --
CREATE TABLE Groups (
 	groupId		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  	name        	VARCHAR(255) NOT NULL,
  	comment     	VARCHAR(1000) BINARY DEFAULT NULL,
  	PRIMARY KEY 	(groupId),
  	UNIQUE KEY  	(name)
) ENGINE=InnoDB;
--
CREATE TABLE UserGroups (
  	userGroupsId    BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  	userId      	BIGINT(20) UNSIGNED NOT NULL,
  	groupId     	BIGINT(20) UNSIGNED NOT NULL,
  	PRIMARY KEY 	(userGroupsId),
  	INDEX       	(userId),
	INDEX       	(groupId),
  	FOREIGN KEY 	(userId) REFERENCES `User`(userId) ON DELETE CASCADE,
  	FOREIGN KEY 	(groupId) REFERENCES Groups(groupId) ON DELETE CASCADE
) ENGINE=InnoDB;
-- CMS TABLE END --















-- WEBSNMP TABLE BEGIN --
CREATE TABLE SnmpMibFile(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(255) NOT NULL,
	UNIQUE KEY	(name),
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE SnmpOID(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	oid		VARCHAR(500) BINARY DEFAULT NULL,
	mibName		VARCHAR(500) DEFAULT NULL,
	captorName	VARCHAR(500) DEFAULT NULL,
	description	TEXT DEFAULT NULL,
	parentId	BIGINT(20) UNSIGNED,
	mibFileId	BIGINT(20) UNSIGNED,
	INDEX		(parentId),
	INDEX		(mibFileId),
	PRIMARY KEY 	(id),
	FOREIGN KEY	(parentId) REFERENCES SnmpOID(id) ON DELETE CASCADE,
	FOREIGN KEY	(mibFileId) REFERENCES SnmpMibFile(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE SnmpHost(
	snmpHostId		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	displayName 		VARCHAR(255) BINARY DEFAULT NULL,
	ipAddress   		VARCHAR(255) BINARY DEFAULT NULL,
	community   		VARCHAR(500) BINARY DEFAULT NULL,
	userName   		VARCHAR(500) BINARY DEFAULT NULL,
	authentPassword  	VARCHAR(500) BINARY DEFAULT NULL,
	privacyPassword   	VARCHAR(500) BINARY DEFAULT NULL,
	snmpVersion 		ENUM('0','1','2') DEFAULT '0',
	PRIMARY KEY 		(snmpHostId),
	UNIQUE KEY		(displayName),
	UNIQUE KEY		(ipAddress)
) ENGINE=InnoDB;
--
CREATE TABLE SnmpParam(
	snmpParamId	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	snmpHostId   	BIGINT(20) UNSIGNED DEFAULT NULL,
	oid          	VARCHAR(500) DEFAULT NULL,
	PRIMARY KEY  	(snmpParamId),
	INDEX        	(snmpHostId),
	FOREIGN KEY  	(snmpHostId) REFERENCES SnmpHost(snmpHostId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE SnmpValue(
	snmpValueId 	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	snmpParamId 	BIGINT(20) UNSIGNED DEFAULT NULL,
	dateTime       	DATETIME DEFAULT NULL,
	PRIMARY KEY 	(snmpValueId),
	INDEX       	(snmpParamId),
	FOREIGN KEY 	(snmpParamId) REFERENCES SnmpParam(snmpParamId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE SnmpReport(
	reportId 	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	displayName	VARCHAR(255) BINARY NOT NULL,
	frequency	BIGINT(20) UNSIGNED DEFAULT '300',
	hostId		BIGINT(20) UNSIGNED NOT NULL,
	timeRange	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(reportId),
	INDEX		(hostId),
	FOREIGN KEY	(hostId) REFERENCES SnmpHost(snmpHostId) ON DELETE CASCADE,
	UNIQUE KEY	(displayName)
) ENGINE=InnoDB;
--
CREATE TABLE SnmpFormula(
	formulaId	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	displayName	VARCHAR(255) BINARY DEFAULT NULL,
	description	TEXT DEFAULT NULL,
	formula		VARCHAR(255) BINARY DEFAULT NULL,
	legend		VARCHAR(500) DEFAULT NULL,
	PRIMARY KEY	(formulaId),
	UNIQUE KEY	(displayName),
	UNIQUE KEY	(formula)
) ENGINE=InnoDB;
--
CREATE TABLE SnmpGraphicFormula(
	gFormulaId	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,	
	reportId	BIGINT(20) UNSIGNED DEFAULT NULL,
	formulaId	BIGINT(20) UNSIGNED DEFAULT NULL,
	lastUpdateTime	DATETIME DEFAULT NULL,
	`condition`	ENUM('lt','gt','eq') DEFAULT NULL,
	value		DOUBLE DEFAULT NULL,
	PRIMARY KEY	(gFormulaId),
	INDEX		(reportId),
	INDEX		(formulaId),
	FOREIGN KEY	(reportId) REFERENCES SnmpReport(reportId) ON DELETE CASCADE,
	FOREIGN KEY	(formulaId) REFERENCES SnmpFormula(formulaId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE SnmpGFValue(
	gfValueId	BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	gFormulaId	BIGINT(20) UNSIGNED DEFAULT NULL,
	dateTime	DATETIME DEFAULT NULL,
	PRIMARY KEY	(gfValueId),
	INDEX		(gFormulaId),
	FOREIGN KEY	(gFormulaId) REFERENCES SnmpGraphicFormula(gFormulaId) ON DELETE CASCADE
) ENGINE=InnoDB;

--
CREATE TABLE SnmpGFValueData(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	gfValueId	BIGINT(20) UNSIGNED,
	value		DOUBLE DEFAULT NULL,
	PRIMARY KEY	(id),
	INDEX		(gfValueId),
	FOREIGN KEY	(gfValueId) REFERENCES SnmpGFValue(gfValueId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE SnmpValueData(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	snmpValueId	BIGINT(20) UNSIGNED,
	value		TEXT DEFAULT NULL,
	PRIMARY KEY	(id),
	INDEX		(snmpValueId),
	FOREIGN KEY	(snmpValueId) REFERENCES SnmpValue(snmpValueId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE SnmpAction(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(500) NOT NULL,
	hostId		BIGINT(20) UNSIGNED,
	type		ENUM('Get','Set') DEFAULT 'Get',
	oid		BIGINT(20) UNSIGNED,
	PRIMARY KEY	(id),
	INDEX		(hostId),
	INDEX		(oid),
	FOREIGN KEY	(hostId) REFERENCES SnmpHost(snmpHostId) ON DELETE CASCADE,
	FOREIGN KEY	(oid) REFERENCES SnmpOID(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE SnmpAlertUser(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	groupId		BIGINT(20) UNSIGNED,
	PRIMARY KEY	(id),
	INDEX		(groupId),
	FOREIGN KEY	(groupId) REFERENCES Groups(groupId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE SnmpAlert(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	type		ENUM('Polling','Trap') DEFAULT NULL,
	source		VARCHAR(500) DEFAULT NULL,
	description	TEXT DEFAULT NULL,
	level		ENUM('Debug','Informational','Notice','Warning','Error','Critical','Alert','Emergency') DEFAULT NULL,
	date		DATETIME DEFAULT NULL,
	reportId	BIGINT(20) UNSIGNED DEFAULT NULL,
	PRIMARY KEY	(id),
	INDEX		(reportId),
	FOREIGN KEY	(reportId) REFERENCES SnmpReport(reportId) ON DELETE SET NULL
) ENGINE=InnoDB;
-- WEBSNMP TABLE END --






-- CMS BEGIN --
CREATE TABLE CmsModule(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(500) NOT NULL,
	visible		SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	css		TEXT DEFAULT NULL,
	auth		ENUM('specific','allgroups','anonymous'),
	advConf		SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	advUrl		VARCHAR(500) DEFAULT NULL,
	jsp		VARCHAR(500),
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE CmsBlocks(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	weight		SMALLINT UNSIGNED NOT NULL default '0',
	visibleIn	ENUM('all','first','list') DEFAULT 'all',
	visible		SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	position	ENUM('left','center','right') DEFAULT 'left',
	title		VARCHAR(500) NOT NULL,
	moduleId	BIGINT(20) UNSIGNED NOT NULL,
	jsp		VARCHAR(500) DEFAULT NULL,
	INDEX		(moduleId),
	FOREIGN KEY	(moduleId) REFERENCES CmsModule(id) ON DELETE CASCADE,
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE CmsPreferences(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	css		TEXT,
	smtpHost	VARCHAR(500),
	ipAddress	VARCHAR(500) DEFAULT NULL,
	firstPageId	BIGINT(20) UNSIGNED,
	header		TEXT,
	footer		TEXT,
	logoId		BIGINT(20) UNSIGNED,
	siteTitle	VARCHAR(500),
	debugLog	SMALLINT UNSIGNED DEFAULT '0',
	cmsLang		ENUM('en','fr'),
	ldapAuthent	SMALLINT UNSIGNED DEFAULT '0',
	ldapDN		VARCHAR(500),
	ldapServer	VARCHAR(500),
	PRIMARY KEY	(id),
	INDEX		(firstPageId),
	INDEX		(logoId),
	FOREIGN KEY	(firstPageId) REFERENCES CmsModule(id) ON DELETE SET NULL,
	FOREIGN KEY	(logoId) REFERENCES FileBase(fileBaseId) ON DELETE SET NULL
) ENGINE=InnoDB;

--
CREATE TABLE CmsBLockVisibility(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	blockId		BIGINT(20) UNSIGNED NOT NULL,
	moduleId	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(blockId),
	INDEX		(moduleId),
	FOREIGN KEY	(blockId) REFERENCES CmsBlocks(id) ON DELETE CASCADE,
	FOREIGN KEY	(moduleId) REFERENCES CmsModule(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE CmsModuleAuthorization(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	moduleId	BIGINT(20) UNSIGNED NOT NULL,
	groupId		BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(moduleId),	
	INDEX		(groupId),
	FOREIGN KEY	(moduleId) REFERENCES CmsModule(id) ON DELETE CASCADE,
	FOREIGN KEY	(groupId) REFERENCES Groups(groupId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE CmsInbox(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	isRead		SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	date		DATETIME NOT NULL,
	fromUserId	BIGINT(20) UNSIGNED,
	toUserId	BIGINT(20) UNSIGNED NOT NULL,
	subject		VARCHAR(500),
	priority	ENUM('normal','high') DEFAULT 'normal',
	text		TEXT,
	PRIMARY KEY	(id),
	INDEX		(fromUserId),
	INDEX		(toUserId),
	FOREIGN KEY	(fromUserId) REFERENCES `User`(userId) ON DELETE SET NULL,
	FOREIGN KEY	(toUserId) REFERENCES `User`(userId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE CmsInboxAttachment(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	fileBaseId	BIGINT(20) UNSIGNED NOT NULL,
	inboxId		BIGINT(20) UNSIGNED NOT NULL,
	INDEX		(fileBaseId),
	INDEX		(inboxId),
	PRIMARY KEY	(id),
	FOREIGN KEY	(fileBaseId) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE,
	FOREIGN KEY	(inboxId) REFERENCES CmsInbox(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE CmsInboxFriends(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	userId		BIGINT(20) UNSIGNED NOT NULL,
	friendId	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(userId),
	INDEX		(friendId),
	FOREIGN KEY	(userId) REFERENCES `User`(userId) ON DELETE CASCADE,
	FOREIGN KEY	(friendId) REFERENCES `User`(userId) ON DELETE CASCADE
) ENGINE=InnoDB;
-- CMS END --


-- ARTICLE BEGIN --
CREATE TABLE ArticlePref(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	articlePerPage	SMALLINT UNSIGNED NOT NULL DEFAULT '10',
	comments	SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE ArticleCategory(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(255),
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE Article(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	date		DATETIME NOT NULL,
	title		VARCHAR(500),
	categoryId	BIGINT(20) UNSIGNED NOT NULL,
	summary		TEXT,
	text		TEXT,
	validate	SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	imageId		BIGINT(20) UNSIGNED,
	imageLegend	VARCHAR(500),
	authorId	BIGINT(20) UNSIGNED,
	moderatorId	BIGINT(20) UNSIGNED,
	PRIMARY KEY	(id),
	INDEX		(categoryId),
	INDEX		(imageId),
	INDEX		(authorId),
	INDEX		(moderatorId),
	FOREIGN KEY	(categoryId) REFERENCES ArticleCategory(id) ON DELETE CASCADE,
	FOREIGN KEY	(imageId) REFERENCES FileBase(fileBaseId) ON DELETE SET NULL,
	FOREIGN KEY	(authorId) REFERENCES `User`(userId) ON DELETE SET NULL,
	FOREIGN KEY	(moderatorId) REFERENCES `User`(userId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE ArticleModerator(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	categoryId	BIGINT(20) UNSIGNED NOT NULL,
	userId		BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(categoryId),
	INDEX		(userId),
	FOREIGN KEY	(categoryId) REFERENCES ArticleCategory(id) ON DELETE CASCADE,
	FOREIGN KEY	(userId) REFERENCES `User`(userId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE ArticleComment(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	articleId	BIGINT(20) UNSIGNED NOT NULL,
	authorId	BIGINT(20) UNSIGNED,
	date		DATETIME NOT NULL,
	title		VARCHAR(500),
	comment		TEXT,
	PRIMARY KEY	(id),
	INDEX		(articleId),
	INDEX		(authorId),
	FOREIGN KEY	(articleId) REFERENCES Article(id) ON DELETE CASCADE,
	FOREIGN KEY	(authorId) REFERENCES `User`(userId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE ArticleFile(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	articleId	BIGINT(20) UNSIGNED NOT NULL,
	fileBaseId	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(articleId),
	INDEX		(fileBaseId),
	FOREIGN KEY	(articleId) REFERENCES Article(id) ON DELETE CASCADE,
	FOREIGN KEY	(fileBaseId) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE ArticleLink(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,	
	articleId	BIGINT(20) UNSIGNED NOT NULL,
	link		VARCHAR(500) NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(articleId),
	FOREIGN KEY	(articleId) REFERENCES Article(id) ON DELETE CASCADE
) ENGINE=InnoDB;
-- ARTICLE END --





-- GED BEGIN --
CREATE TABLE GedPref (
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	trashExpiry	BIGINT(20) UNSIGNED NOT NULL,
	stateReaderGrp BIGINT(20) UNSIGNED,
	PRIMARY KEY	(id),
	INDEX		(stateReaderGrp),
	FOREIGN KEY (stateReaderGrp) REFERENCES Groups(groupId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE GedWorkflow(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(500) NOT NULL,
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE GedWorkflowStep(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(500) NOT NULL,
	sendTo1		BIGINT(20) UNSIGNED NOT NULL,
	sendTo2		BIGINT(20) UNSIGNED,
	timelimit1	BIGINT(20) UNSIGNED NOT NULL,
	timelimit2	BIGINT(20) UNSIGNED,
	weight		SMALLINT UNSIGNED NOT NULL default '0',
	workflowId	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	index		(workflowId),
	index		(sendTo1),
	index		(sendTo2),
	FOREIGN KEY	(workflowId) REFERENCES GedWorkflow(id) ON DELETE CASCADE,
	FOREIGN KEY	(sendTo1) REFERENCES `User`(userId) ON DELETE CASCADE,
	FOREIGN KEY	(sendTo2) REFERENCES `User`(userId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE GedTemplate(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(500) NOT NULL,
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE GedTemplateItem(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	fieldname	VARCHAR(500) NOT NULL,
	size		SMALLINT UNSIGNED,
	type		ENUM('free','date','enumeration') DEFAULT 'free',
	enumeration	VARCHAR(3000),
	defaultValue	VARCHAR(500),
	weight		SMALLINT UNSIGNED NOT NULL default '0',
	templateId	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(templateId),
	FOREIGN KEY	(templateId) REFERENCES GedTemplate(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE Thesaurus(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(500) NOT NULL,
	PRIMARY KEY	(id)
) ENGINE=InnoDB;
--
CREATE TABLE ThesaurusItem(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	text		VARCHAR(500) NOT NULL,
	thesaurusId	BIGINT(20) UNSIGNED NOT NULL,
	parentItemId 	BIGINT(20) UNSIGNED,
	PRIMARY KEY	(id),
	INDEX		(thesaurusId),
	INDEX		(parentItemId),
	FOREIGN KEY	(thesaurusId) REFERENCES  Thesaurus(id) ON DELETE CASCADE,
	FOREIGN KEY	(parentItemId) REFERENCES ThesaurusItem(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE GedDirectory(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(500) NOT NULL,
	parentDirId	BIGINT(20) UNSIGNED,
	templateId	BIGINT(20) UNSIGNED,
	fileExpiryTime	BIGINT(20) UNSIGNED,
	fileExpiryGroup	BIGINT(20) UNSIGNED,
	fileAddModifyWorkflow	BIGINT(20) UNSIGNED,
	reference		VARCHAR(500) NOT NULL,
	path		VARCHAR(3000) NOT NULL,
	storeType	ENUM('normal','readonlyinpdf','allinpdf') DEFAULT 'normal',
	PRIMARY KEY	(id),
	INDEX		(parentDirId),
	INDEX		(templateId),
	INDEX		(fileExpiryGroup),
	INDEX		(fileAddModifyWorkflow),
	FOREIGN KEY	(parentDirId) REFERENCES GedDirectory(id) ON DELETE CASCADE,
	FOREIGN KEY	(templateId) REFERENCES GedTemplate(id) ON DELETE SET NULL,
	FOREIGN KEY	(fileExpiryGroup) REFERENCES Groups(groupId) ON DELETE SET NULL,
	FOREIGN KEY	(fileAddModifyWorkflow) REFERENCES GedWorkflow(id) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE GedDirectoryRights(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	directoryId	BIGINT(20) UNSIGNED NOT NULL,
	groupId		BIGINT(20) UNSIGNED NOT NULL,
	readRight	SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	writeRight	SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	PRIMARY KEY	(id),
	INDEX		(directoryId),
	INDEX		(groupId),
	FOREIGN KEY	(directoryId) REFERENCES GedDirectory(id) ON DELETE CASCADE,
	FOREIGN KEY	(groupId) REFERENCES Groups(groupId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE GedDirThesaurus(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	directoryId	BIGINT(20) UNSIGNED NOT NULL,
	thesaurusId	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(directoryId),
	INDEX		(thesaurusId),
	FOREIGN KEY	(directoryId) REFERENCES GedDirectory(id) ON DELETE CASCADE,
	FOREIGN KEY	(thesaurusId) REFERENCES Thesaurus(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE GedFiles(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	directoryId	BIGINT(20) UNSIGNED NOT NULL,
	lockBySyst	SMALLINT UNSIGNED NOT NULL DEFAULT '0',
	lockByUser	BIGINT(20) UNSIGNED,
	PRIMARY KEY	(id),
	INDEX		(directoryId),
	INDEX		(lockByUser),
	FOREIGN KEY	(directoryId) REFERENCES GedDirectory(id) ON DELETE CASCADE,
	FOREIGN KEY	(lockByUser) REFERENCES `User`(userId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE GedFilesComment(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	fileId		BIGINT(20) UNSIGNED NOT NULL,
	userId		BIGINT(20) UNSIGNED,
	subject		VARCHAR(500) NOT NULL,
	text		TEXT,
	PRIMARY KEY	(id),
	INDEX		(fileId),
	INDEX		(userId),
	FOREIGN KEY	(fileId) REFERENCES GedFiles(id) ON DELETE CASCADE,
	FOREIGN KEY	(userId) REFERENCES `User`(userId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE GedFilesDescr(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	templateItemId	BIGINT(20) UNSIGNED NOT NULL,
	value		VARCHAR(500) NOT NULL,
	fileId		BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(templateItemId),
	INDEX		(fileId),
	FOREIGN KEY	(templateItemId) REFERENCES GedTemplateItem(id) ON DELETE CASCADE,
	FOREIGN KEY	(fileId) REFERENCES GedFiles(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE GedFilesVersion(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	fileId		BIGINT(20) UNSIGNED NOT NULL,
	fileBaseId	BIGINT(20) UNSIGNED NOT NULL,
	visible		SMALLINT UNSIGNED NOT NULL DEFAULT '1',
	version		BIGINT(20) UNSIGNED NOT NULL,
	reference	VARCHAR(500) NOT NULL,
	keywords	TEXT,
	name		VARCHAR(500) NOT NULL,
	size		BIGINT(20) UNSIGNED,
	authorId	BIGINT(20) UNSIGNED,
	versionDate	DATETIME,
	PRIMARY KEY	(id),
	INDEX		(fileId),
	INDEX		(fileBaseId),
	INDEX		(authorId),
	FOREIGN KEY	(fileId) REFERENCES GedFiles(id) ON DELETE CASCADE,
	FOREIGN KEY	(authorId) REFERENCES `User`(userId) ON DELETE SET NULL,

	FOREIGN KEY	(fileBaseId) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE GedWorkflowProc(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,	
	fileId		BIGINT(20) UNSIGNED NOT NULL,
	workflowStepId	BIGINT(20) UNSIGNED NOT NULL,
	beginDate	DATETIME NOT NULL,
	status		ENUM('Approved','Disapproved','Sent','SentToBackup','New','Expired') DEFAULT 'New',
	PRIMARY KEY	(id),
	UNIQUE		(fileId),
	INDEX		(fileId),
	INDEX		(workflowStepId),
	FOREIGN KEY	(fileId) REFERENCES GedFiles(id) ON DELETE CASCADE,
	FOREIGN KEY	(workflowStepId) REFERENCES GedWorkflowStep(id) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE GedTrash (
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	fileId		BIGINT(20) UNSIGNED NOT NULL,
	expiryTime	DATETIME NOT NULL,
	ownerId		BIGINT(20) UNSIGNED,
	PRIMARY KEY	(id),
	UNIQUE KEY	(fileId),
	INDEX		(fileId),
	INDEX		(ownerId),
	FOREIGN KEY	(fileId) REFERENCES GedFiles(id) ON DELETE CASCADE,
	FOREIGN KEY	(ownerId) REFERENCES `User`(userId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
CREATE TABLE GedNotification (
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	directoryId	BIGINT(20) UNSIGNED NOT NULL,
	groupId		BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(directoryId),
	INDEX		(groupId),
	FOREIGN KEY	(directoryId) REFERENCES GedDirectory(id) ON DELETE CASCADE,
	FOREIGN KEY	(groupId) REFERENCES Groups(groupId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE GedState (
	id			BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	generalColumn	ENUM('filename','dirname','author','date','reference','version','procstatus','expirydate','procstep','proccurrentuser','lockby'),
	gedTemplateItem	BIGINT(20) UNSIGNED,
	next			BIGINT(20) UNSIGNED,
	PRIMARY KEY	(id),
	INDEX			(gedTemplateItem),
	INDEX			(next),
	FOREIGN KEY		(gedTemplateItem) REFERENCES GedTemplateItem(id) ON DELETE SET NULL,
	FOREIGN KEY		(next) REFERENCES GedState(id) ON DELETE SET NULL
) ENGINE=InnoDB;
-- GED END --



-- MAIL ARCHIVE BEG --
CREATE TABLE MailArchive (
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	mailAccountId	BIGINT(20) UNSIGNED NOT NULL,
	headerMessageId	VARCHAR(255),
	date		DATETIME,
	fromMail	VARCHAR(500),
	toMail		TEXT,
	subject		VARCHAR(500),
	messageId	BIGINT(20) UNSIGNED,
	rawId		BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	UNIQUE		(headerMessageId),
	INDEX		(mailAccountId),
	INDEX		(messageId),
	INDEX		(rawId),
	FOREIGN KEY	(mailAccountId) REFERENCES `User`(userId) ON DELETE CASCADE,
	FOREIGN KEY	(messageId) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE,
	FOREIGN KEY	(rawId) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE
) ENGINE=InnoDB;
--
CREATE TABLE MailArchiveAttach(
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	mailArchiveId	BIGINT(20) UNSIGNED NOT NULL,
	attachmentId	BIGINT(20) UNSIGNED NOT NULL,
	PRIMARY KEY	(id),
	INDEX		(mailArchiveId),
	INDEX		(attachmentId),
	FOREIGN KEY	(mailArchiveId) REFERENCES MailArchive(id) ON DELETE CASCADE,
	FOREIGN KEY	(attachmentId) REFERENCES FileBase(fileBaseId) ON DELETE CASCADE
) ENGINE=InnoDB;
-- MAIL ARCHIVE END --


--
CREATE TABLE Trace (
	id		BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	date		DATETIME NOT NULL,
	moduleId	BIGINT(20) UNSIGNED DEFAULT NULL,
	object		VARCHAR(500) NOT NULL,
	action		ENUM('index','save','delete','submit','recover','expire', 'move', 'notify','approve','disapprove','revise'),
	statusMsg	ENUM('fail','ok'),
	statusUserId	BIGINT(20) UNSIGNED DEFAULT NULL,
	statusGroupId	BIGINT(20) UNSIGNED DEFAULT NULL,
	ipAddress	VARCHAR(500) DEFAULT NULL,
	PRIMARY KEY	(id),	
	INDEX		(moduleId),
	INDEX		(statusUserId),
	INDEX		(statusGroupId),
	FOREIGN KEY	(moduleId) REFERENCES CmsModule(id) ON DELETE SET NULL,
	FOREIGN KEY	(statusUserId) REFERENCES `User`(userId) ON DELETE SET NULL,
	FOREIGN KEY	(statusGroupId) REFERENCES Groups(groupId) ON DELETE SET NULL
) ENGINE=InnoDB;
--
