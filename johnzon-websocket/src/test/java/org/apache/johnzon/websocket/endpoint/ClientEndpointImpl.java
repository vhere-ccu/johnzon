/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.johnzon.websocket.endpoint;

import org.apache.johnzon.websocket.mapper.JohnzonTextDecoder;
import org.apache.johnzon.websocket.mapper.JohnzonTextEncoder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.websocket.ClientEndpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;

@ClientEndpoint(encoders = JohnzonTextEncoder.class, decoders = ClientEndpointImpl.MessageDecoder.class)
public class ClientEndpointImpl {
    public static final List<Message> MESSAGES = new LinkedList<Message>();
    public static final Semaphore SEMAPHORE = new Semaphore(0);

    @OnOpen
    public void init(final EndpointConfig config) {

    }

    @OnMessage
    public synchronized void on(final Message message) {
        MESSAGES.add(message);
        SEMAPHORE.release();
    }

    public static class MessageDecoder extends JohnzonTextDecoder {
        public MessageDecoder() {
            type = Message.class;
        }
    }
}