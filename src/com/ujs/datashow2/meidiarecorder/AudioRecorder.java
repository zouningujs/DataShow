package com.ujs.datashow2.meidiarecorder;

import java.io.File;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * 工具类,声音录取和播放
 * 
 * @author zouning
 * 
 */
public class AudioRecorder {
    private static final String tag = "AudioRecorder";
    private Context mContext;
    private MediaRecorder mediaRecorder;

    public AudioRecorder(Context context) {
        mContext = context;
        mediaRecorder = new MediaRecorder();
    }

    /**
     * 从麦克风录制声音,格式为amr格式
     * 
     * @param directory
     *            存储文件的路径
     * @param fileName
     *            文件名,无需后缀
     * @return
     */
    public boolean recordAsAMR(String directory, String fileName) {
        Log.d(tag, "record");
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mContext, "请插入SD卡", Toast.LENGTH_LONG).show();
            Log.w(tag, "没有插入sd卡");
            return false;
        }
        try {
            // 创建音频输出的文件路径
            File soundFile = new File(directory + File.separator + fileName
                    + ".amr");
            // 设置录音的来源为麦克风
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置录制的声音输出格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            // 设置声音的编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // 设置录音的输出文件路径
            mediaRecorder.setOutputFile(soundFile.getAbsolutePath());

            // 做预期准备
            mediaRecorder.prepare();
            mediaRecorder.start();

            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(tag, e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 停止录制
     * 
     * @return
     */
    public boolean stop() {
        mediaRecorder.stop();
        mediaRecorder.release();
        return true;
    }

}
