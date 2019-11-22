package priv.raigor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;

/**
 * 应用服务 server
 *
 * @author raigor
 * @version 1.0, Feb 16, 2019
 */
public class AppServer {


	private static final Logger LOG = LoggerFactory.getLogger(AppServer.class);


	private ClassPathXmlApplicationContext context;


	// ~ --------------------------------------------------------------------------------------------------------


	public static void main(String[] args) {
		long s = System.currentTimeMillis();
		LOG.info("[APP-SERVER] App server startup ...");
        LOG.debug("gggrr");
		try {
			new AppServer().startup();
		} catch (Exception e) {
			LOG.error("APP-SERVER] App server startup fail!", e);
			System.exit(1);
		}

		long e = System.currentTimeMillis();
		LOG.info("[APP-SERVER] App server startup ... end, elapse {} ms", (e - s));
		int temp = 1;
		synchronized (AppServer.class) {
			while (true) {
				try {
					if(temp==1){
						AppServer.class.wait();
					}
					if(temp==2){
						break;
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}


	// ~ --------------------------------------------------------------------------------------------------------


    /**
     * 类加载器
     * context:component-scan 标签是怎么发生作用的
     * @throws IOException
     */
	private void startup() throws IOException {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:/spring/spring-dd-unimessage.xml"});
//		context = new ClassPathXmlApplicationContext(new String[] { "classpath:/spring/spring-dd-unimessage-test.xml" });
		context.start();
	}


}
