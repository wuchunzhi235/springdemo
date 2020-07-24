package priv.raigor.trial.spring.sourcecodeanalysis.handler;

import priv.raigor.trial.spring.sourcecodeanalysis.handler.AppHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component(value = "chatMessageHandlerInDuoMessage")
public class ChatMessageHandler  implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ChatMessageHandler.class);


    @Autowired
    @Qualifier("chatMessageHandlerImpl")
    AppHandlerService appHandlerService;

    @Value("${im.single.chat.appid.ump.key:im4.1-dd-unimessage.chat_message.dd0}")
    private String umpKey;

    @Value("${im.single.chat.appid.ump.appname:im4.1-dd-unimessage}")
    private String appName;


    public void afterPropertiesSet() throws Exception {
        LOG.info(" here is ChatMessageHandler afterPropertiesSet ???? ");
    }
}
