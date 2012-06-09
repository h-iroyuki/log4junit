package log4junit;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * �e�X�g�plog4j�A�y���_�[�B
 * 
 * log4j�ŏo�͂��郁�b�Z�[�W�����������1000�����߂Ă����A<br />
 * ���̃��b�Z�[�W�����݂��邩�ǂ������e�X�g���郂�W���[���B<br />
 */
public class MemoryTestAppender extends AppenderSkeleton{
    /** ���O�̒��ߍ��݃G���A */
    private static Queue<String> line = new ConcurrentLinkedQueue<String>();

    /** ���O�̍ő匏�� */
    private static final int MAX_LINE = 1000;

    /**
     * ������`�F�b�N�����B
     * 
     * @param search ����������B(�ꕔ�ł��}�b�`������OK)
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
     * ��������̃��O�̏����������B
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
