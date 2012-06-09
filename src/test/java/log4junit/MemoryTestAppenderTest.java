package log4junit;

import org.apache.log4j.Logger;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * MemoryTestAppnder�̃e�X�g�B
 */
public class MemoryTestAppenderTest extends TestCase {
    /** �e�X�g�Ώ� */
    private Logger logger = Logger.getLogger(MemoryTestAppenderTest.class);

    /**
     * ���O�����B
     * �������̃N���A�B
     */
    public void setUp(){
        MemoryTestAppender.clear();
    }

    public void testAssertContains�������������̂�(){
        try{
            MemoryTestAppender.assertContains("hoge");
            fail();
        }catch(AssertionFailedError e){
        }

        logger.info("hoge");
        MemoryTestAppender.assertContains("INFO MemoryTestAppenderTest - hoge");
    }

    public void testAppend�ŏ����������폜����Ă�̂�(){
        for( int i = 0; i < 10001; i++ ){
            logger.info("hoge" + i);
        }

        try{
            MemoryTestAppender.assertContains("hoge0");
            fail();
        }catch( AssertionFailedError e){
        }
        MemoryTestAppender.assertContains("hoge1");
    }
}
