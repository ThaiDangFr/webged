CREATE DATABASE webged;
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON webged.* TO 'dcuser'@'localhost' IDENTIFIED BY 'motdepasse';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON webged.* TO 'dcuser'@'%' IDENTIFIED BY 'motdepasse';