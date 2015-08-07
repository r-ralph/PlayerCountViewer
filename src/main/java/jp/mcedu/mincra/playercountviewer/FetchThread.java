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

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class FetchThread extends Thread {
    private final JedisPool pool;
    private final JLabel label_num;
    private AtomicBoolean stop = new AtomicBoolean(false);

    public FetchThread(JLabel label_num) {
        this.label_num = label_num;
        pool = new JedisPool(new JedisPoolConfig(), "192.168.11.10", 6379, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
    }

    @Override
    public void run() {
        while (!stop.get()) {
            try (Jedis jedis = pool.getResource()) {
                sleep(1000);
                String num = jedis.get("player_num");
                SwingUtilities.invokeLater(() -> label_num.setText(num + "人"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            sleep(100);
        } catch (InterruptedException ignored) {
        }
        notifyAll();
    }

    public synchronized void stopThread() {
        stop.set(true);
        try {
            this.wait(1000);
        } catch (InterruptedException ignored) {
        }
    }
}

