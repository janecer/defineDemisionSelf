import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * author锛歫anecer on 2015/10/11 12:12
 * email锛歫anecer@sina.cn
 */

public class MakeXml {
	
    private final static String rootPath = "C:/Users/PC/Desktop/values/values-{0}x{1}/" ;

    private final static float dw = 320f ;
    private final static float dh = 480f ;

    private  static String WTemplete = "<dimen name=\"x{0}\">{1}px</dimen>\n" ;
    private  static String HTemplete = "<dimen name=\"y{0}\">{1}px</dimen>\n" ;

    private static boolean isFirst  = true ;
    private static boolean isMakeBase = false ;

    public static void main(String[] args){
    	System.out.println("11");
    	long time = System.currentTimeMillis() ;
        makeString(320,480);
        makeString(480,800);
        makeString(480,854);
        makeString(540,960);
        makeString(600,1024);
        makeString(720,1184);
        makeString(720,1196);
        makeString(720,1280);
        makeString(768,1024);
        makeString(800,1280);
        makeString(1080,1812);
        makeString(1080,1920);
        makeString(1440,2560);
        System.out.println(System.currentTimeMillis() - time);
    }

    public static void makeString(int w,int h ){
    	
    	if(isFirst){ //自动生成一个dp单位的文件
    		isFirst = false ;
    		isMakeBase = true ;
    		makeString((int)dw,(int)dh) ;
    	}
    	
        StringBuffer sb = new StringBuffer() ;
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n") ;
        sb.append("<resources>\n") ;
        float cellw = w / dw ;
        for(int i = 1 ;i < dw ;i++){
            sb.append(WTemplete.replace("{0}",i + "").replace("{1}",cellw * i +"")) ;
        }
        sb.append(WTemplete.replace("{0}",(int)dw + "").replace("{1}",w +"")) ;
        sb.append("</resources>") ;

        StringBuffer sb2 = new StringBuffer() ;
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n") ;
        sb2.append("<resources>") ;
        float cellh = h/dh ;
        for(int i = 1 ;i < dh ;i++){
            sb2.append(WTemplete.replace("{0}",i + "").replace("{1}",cellh * i +"")) ;
        }
        sb2.append(WTemplete.replace("{0}",(int)dh + "").replace("{1}",h +"")) ;
        sb2.append("</resources>") ;

        String path ;
        String sbResult = sb.toString() ;
        String sbResult2 = sb2.toString() ;
        if(isMakeBase){
        	path = rootPath.replace("-{0}x{1}", "") ;
        	isMakeBase = false ;
        	
		    sbResult = sbResult.replaceAll("px", "dp") ;
		    sbResult2 = sbResult2.replaceAll("px", "dp") ;
		    System.out.println(sbResult+ "   " +sbResult.contains("px"));
        } else {
        	path = rootPath.replace("{0}",h + "").replace("{1}",w+"") ;
        }
        
        File rootFile = new File(path) ;
        if(!rootFile.exists()){
            rootFile.mkdirs() ;
        }
        File layxFile = new File(path + "lay_x.xml") ;
        File layyFile = new File(path + "lay_y.xml") ;

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile)) ;
            pw.print(sbResult.toString());
            pw.close();

            pw = new PrintWriter(new FileOutputStream(layyFile)) ;
            pw.print(sbResult2.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
