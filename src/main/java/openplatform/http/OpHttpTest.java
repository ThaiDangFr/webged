package openplatform.http;

/**
 * OpHttpTest.java
 *
 *
 * Created: Sun Jun 26 14:30:37 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class OpHttpTest 
{
    public static void main(String[] args)
    {
        OpHttp http = new OpHttp();
        HttpRequest req = new HttpRequest();
        req.setURL("http://acacias/openplatform/cms/user/pc/modules/cmsbase/index.jsp");
        req.addParamValuePair("username","admin");
        req.addParamValuePair("password","admin");
        HttpResponse resp = http.POST(req);
    }
}
