INSERT INTO MimeTypes (extension, primarytype, secondarytype) VALUES 
('exe','application','octet-stream'),
('pdf','application','pdf'),
('ps','application','postscript'),
('smil','application','smil'),
('ppt','application','vnd.ms-powerpoint'),
('js','application','x-javascript'),
('latex','application','x-latex'),
('swf','application','x-shockwave-flash'),
('tar','application','x-tar'),
('tcl','application','x-tcl'),
('zip','application','zip'),
('doc','application','msword'),
('war','application','x-java-archive'),
('jar','application','x-java-archive'),
('xls','application','vnd.ms-excel'),

('mid','audio','midi'),
('midi','audio','midi'),
('mp3','audio','mpeg'),
('mp2','audio','mpeg'),
('ram','audio','x-pn-realaudio'),
('rm','audio','x-pn-realaudio'),
('ra','audio','x-realaudio'),
('wav','audio','x-wav'),

('gif','image','gif'),
('ief','image','ief'),
('jpeg','image','jpeg'),
('jpg','image','jpeg'),
('jpe','image','jpeg'),
('wbmp','image','vnd.wap.wbmp'),
('png','image','png'),
('tiff','image','tiff'),
('tif','image','tiff'),
('ras','image','x-cmu-raster'),
('pnm','image','x-portable-anymap'),
('pbm','image','x-portable-bitmap'),
('pgm','image','x-portable-graymap'),
('ppm','image','x-portable-pixmap'),
('rgb','image','x-rgb'),
('xbm','image','x-xbitmap'),
('xpm','image','x-xpixmap'),
('xwd','image','x-xwindowdump'),
('bmp','image','bmp'),
('eps','image','x-eps'),
('epsi','image','x-eps'),
('epsf','image','x-eps'),

('css','text','css'),
('asc','text','plain'),
('txt','text','plain'),
('rtf','text','rtf'),
('sgml','text','sgml'),
('sgm','text','sgml'),
('tsv','text','tab-separated-values'),
('etx','text','x-setext'),
('xml','text','xml'),
('html','text','html'),
('xhtml','text','html'),

('mpeg','video','mpeg'),
('mpg','video','mpeg'),
('mpe','video','mpeg'),
('qt','video','quicktime'),
('mov','video','quicktime'),
('avi','video','x-msvideo'),
('movie','video','x-sgi-movie'),
('wmv','video','x-ms-wmv')
;












INSERT INTO License VALUES (1,NULL);




INSERT INTO Image (imageId, width, height, extension) VALUES 
(1,80,60,'jpg'),
(2,400,300,'png'),
(3,96,65,'gif'),
(4,128,96,'jpg'),
(5,96,65,'wbmp'),
(6,220,220,'gif'),
(7,400,300,'jpg');




INSERT INTO Groups VALUES 
(1,'admin','The administrator account. With it, you can connect to the administration.'),
(2,'default group','The default group.');





INSERT INTO User (userid, login, password, mail) VALUES
(1, 'admin', 'admin', 'nospam@dangconsulting.fr');




INSERT INTO UserGroups (userid, groupid) VALUES 
(1,1),
(1,2);









INSERT INTO Terminal (useragent, navtype, imageid) VALUES 

('(.*)Windows CE(.*)','pda',6),

('(.*)Opera(.*)','pc',2),
('(.*)Gecko(.*)','pc',2),
('(.*)Windows 98(.*)','pc',2),
('(.*)Windows NT(.*)','pc',2),

('(.*)portalmmm(.*)','imode',4),

('(.*)Alcatel-BH4(.*)','wap',4),
('(.*)MOT-T720(.*)','wap',3),
('(.*)SIE-C55(.*)','wap',5);










INSERT INTO TreeMenu (id, menuId, name, parentId, href, root) VALUES 

(1,1, 'ROOT', 		null, null, 1),
(2,1, 'System', 	1, null, 0),
(3,1, 'Modules', 	2, 'modules.jsp', 0),
(4,1, 'Blocks', 	2, 'blocks.jsp', 0),
(5,1, 'Preferences', 	2, 'preferences.jsp', 0),
(6,1, 'Applimgt',	1, '../../applimgt/admin/index.jsp',0);












INSERT INTO CmsModule
(id,name	,visible,css	,auth		,advConf,advUrl			,jsp) VALUES
(1,'CmsBase'	,0	,null	,'anonymous'	,0	,null			,'cmsbase/index.jsp'),
(2,'GED'	,1	,null	,'allgroups'	,1	,'ged/index.jsp'	,'ged/index.jsp'),
(3,'Article'	,1	,null	,'anonymous'	,1	,'article/index.jsp'	,'article/index.jsp');

--(4,'Gallery'	,0	,null	,'anonymous'	,0	,null			,'gallery/index.jsp'),
--(5,'Task'	,0	,null	,'anonymous'	,0	,null			,'task/index.jsp'),
--(6,'Agenda'	,0	,null	,'anonymous'	,0	,null			,'agenda/index.jsp'),
--(7,'Forum'	,0	,null	,'anonymous'	,0	,null			,'forum/index.jsp'),
--(8,'Contact'	,0	,null	,'anonymous'	,0	,null			,'contact/index.jsp'),
--(9,'Webmail'	,0	,null	,'anonymous'	,0	,null			,'webmail/index.jsp'),
--(10,'Risk'	,0	,null	,'anonymous'	,0	,null			,'risk/index.jsp'),
--(11,'WebSNMP'	,1	,null	,'anonymous'	,1	,'websnmp/index.jsp'	,'websnmp/index.jsp'),
--(4,'Archivel'	,1	,null	,'allgroups'	,0	,null			,'mailarchive/index.jsp');


INSERT INTO CmsBlocks
(id	,weight	,visibleIn	,visible	,position	,title		,moduleId	,jsp) VALUES
(1	,0	,'all'		,1		,'left' 	,'Main menu'	,1		,'cmsbase/mainmenu.jsp'),
(2	,0	,'all'		,1		,'right'	,'User menu'	,1		,'cmsbase/usermenu.jsp'),
(3	,0	,'list'		,1		,'left'		,'Article'	,3		,'article/blockarticle.jsp'),
(4	,0	,'list'		,1		,'right'	,'Search'	,3		,'article/blockfilter.jsp'),
(5	,0	,'list'		,1		,'left'		,'GED'		,2		,'ged/blocksearch.jsp'),
(6	,1	,'list'		,1		,'right'	,'Ged menu'	,2		,'ged/blockaction.jsp'),
(7	,0	,'list'		,1		,'right'	,'Browse'	,2		,'ged/blockgenea.jsp'),
(8	,2	,'list'		,1		,'left'		,'Todo'		,2		,'ged/blocktodo.jsp');

--(8	,0	,'list'		,1		,'left'		,'Menu'		,11		,'websnmp/blockmenu.jsp');



INSERT INTO CmsBLockVisibility
(id	,blockId		,moduleId) VALUES
(1	,3			,3),
(2	,4			,3),
(3	,5			,2),
(4	,6			,2),
(5	,7			,2),
(6	,8			,2);

--(6	,8			,11);



INSERT INTO CmsPreferences
(id	, css	,smtpHost			,ipAddress	,firstPageId	,header	,footer	,logoId	,siteTitle, debugLog, cmsLang, ldapAuthent, ldapDN, ldapServer) VALUES
(1	,null	,'smtp.webged.fr'	,null		,1		,'Welcome to DANG CONSULTING <span>WEB PORTAL</span>'
										,'Copyright &copy; <a href="http://www.dangconsulting.fr">DANG CONSULTING</a>'
										,null
										,'DANG CONSULTING WEB PORTAL'
										,0
										,'fr'
										,0, 'uid=[LOGIN],ou=Users,dc=jboss,dc=org','ldap.dangconsulting.fr'
										);






INSERT INTO ArticleCategory
(id,	name) VALUES
(1,	'Hi-tech');

INSERT INTO ArticleModerator
(id,	categoryId,	userId) VALUES
(1,	1,		1);




INSERT INTO ArticlePref
(id,	articlePerPage,	comments) VALUES
(1,	10,		0);








INSERT INTO GedDirectory
(id	,name		,reference	,path) VALUES
(1	,'Root'		,'[id]'		,'Root/'),
(2	,'Trash'	,'[id]'		,'Trash/');



-- milli seconds (default 1 month)
INSERT INTO GedPref
(id,	trashExpiry, stateReaderGrp) VALUES
(1,	2678400000, 2);
