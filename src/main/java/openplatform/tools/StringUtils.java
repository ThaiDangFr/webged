package openplatform.tools;


public class StringUtils
{
    private StringUtils() {}


    public static String replaceAll(String initial, String oldst, String newst)
    {
        if(oldst==null || newst==null) return initial;
        
        if(oldst.equals(newst)) return initial;

        String newstring = initial;
        
        int offset = 0;
        int idx;

        while((idx=newstring.indexOf(oldst, offset)) != -1)
        {
            newstring = newstring.substring(0, idx) + newst + newstring.substring(idx+oldst.length());
            offset = idx+newst.length();
//             System.out.println(newstring+" offset="+offset);
            
        }
        
        return newstring;
    }

    public static String capitalize(String st)
    {
        if(st == null) return null;

        char[] ca = st.toCharArray();
        ca[0] = Character.toUpperCase(ca[0]);
        
        return new String(ca);
    }

//     public static void main(String[] args)
//     {
//         System.out.println(StringUtils.replaceAll("io4546io54654io","io","ioaa"));
//     }
}
