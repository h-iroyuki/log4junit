package log4junit;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * テスト用log4jアペンダー。
 * 
 * log4jで出力するメッセージをメモリ上に1000件貯めておき、<br />
 * そのメッセージが存在するかどうかをテストするモジュール。<br />
 */
public class MemoryTestAppender extends AppenderSkeleton{
    /** ログの貯め込みエリア */
    private static Queue<String> line = new ConcurrentLinkedQueue<String>();

    /** ログの最大件数 */
    private static final int MAX_LINE = 1000;

    /**
     * 文字列チェック処理。
     * 
     * @param search 検索文字列。(一部でもマッチしたらOK)
     */
    public static void assertContains(String search) {
        for (String curLine : line) {
            if( StringUtils.contains(curLine, search) ){
                return;
            }
        }
        Assert.fail( search + " not found.");
    }

    /**
     * メモリ上のログの初期化処理。
     */
    public static void clear(){
        line.clear();
    }

    @Override
    protected void append(LoggingEvent event) {
        line.add(layout.format(event));

        if( line.size() >= MAX_LINE ){
            line.remove();
        }
    }

    public boolean requiresLayout() {
        return true;
    }

    public void close() {
    }
}
