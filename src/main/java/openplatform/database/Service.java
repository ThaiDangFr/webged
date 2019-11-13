package openplatform.database;


/**
 * Class Service
 *
 * Used to store data from the database and DomMenuNode object
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Service
{
    private String id;
    private String name;
    private String active;
    private String directory;
    private DomMenuNode domMenuNode;


    public String getDirectory()
    {
        return directory;
    }

    public void setDirectory(String adirectory)
    {
        directory=adirectory;
    }

    public String getActive()
    {
        return active;
    }

    public void setActive(String aactive)
    {
        active=aactive;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String aname)
    {
        name=aname;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String aid)
    {
        id=aid;
    }


    public DomMenuNode getDomMenuNode()
    {
        return domMenuNode;
    }

    public void setDomMenuNode(DomMenuNode adomMenuNode)
    {
        domMenuNode=adomMenuNode;
    }
}
