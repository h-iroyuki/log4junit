package log4junit;

import org.apache.log4j.Logger;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * MemoryTestAppnderのテスト。
 */
public class MemoryTestAppenderTest extends TestCase {
    /** テスト対象 */
    private Logger logger = Logger.getLogger(MemoryTestAppenderTest.class);

    /**
     * 事前処理。
     * メモリのクリア。
     */
    public void setUp(){
        MemoryTestAppender.clear();
    }

    public void testAssertContainsが正しく動くのか(){
        try{
            MemoryTestAppender.assertContains("hoge");
            fail();
        }catch(AssertionFailedError e){
        }

        logger.info("hoge");
        MemoryTestAppender.assertContains("INFO MemoryTestAppenderTest - hoge");
    }

    public void testAppendで上限超えたら削除されてるのか(){
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
