package com.example.gustavohenryque.connectingpeople.Interfaces;

import java.util.List;

/**
 * Created by gustavohenryque on 27/09/2016.
 */
public interface TranslationDelegate {
    void voiceReturn(String voice);
    void recognitionError(Integer i);
    void translateReturn(List<String> result);
    void translateError(Exception e);
}
