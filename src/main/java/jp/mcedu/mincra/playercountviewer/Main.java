/*
 * Copyright 2015 TENTO, Mincra, Ralph
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.mcedu.mincra.playercountviewer;

import javax.swing.*;

public class Main {

    private JPanel panel1;
    private JLabel label_num;

    public static void main(String[] args) {
        Main main = new Main();
        JFrame frame = new JFrame("みんなでクラフトプロジェクト");
        frame.setContentPane(main.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        main.startFetchThread();
    }

    private void startFetchThread() {
        //        pool = new JedisPool(new JedisPoolConfig(), config.getMasterAddress(), config.getMasterPort(),
        //              Protocol.DEFAULT_TIMEOUT, config.getMasterPassword(), Protocol.DEFAULT_DATABASE);

        label_num.setText("0人");
        Thread thread = new FetchThread(label_num);
        thread.start();
    }
}
