/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.gxb.lazynetlibrary.net.http.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpCookie;

/**
 * A wrapper class around {@link HttpCookie} and/or {@link HttpCookie} designed for use in {@link
 * PersistentCookieStore}.
 */
public class SerializableCookie implements Serializable {
    private static final long serialVersionUID = 6374381828722046732L;

    private transient final HttpCookie cookie;
    private transient HttpCookie clientCookie;

    public SerializableCookie(HttpCookie cookie) {
        this.cookie = cookie;
    }

    public HttpCookie getCookie() {
        HttpCookie bestCookie = cookie;
        if (clientCookie != null) {
            bestCookie = clientCookie;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(cookie.getName());
        out.writeObject(cookie.getValue());
        out.writeObject(cookie.getPath());
        out.writeObject(cookie.getComment());
        out.writeObject(cookie.getCommentURL());
        out.writeObject(cookie.getDomain());
        out.writeObject(cookie.getPortlist());
        out.writeLong(cookie.getMaxAge());
        out.writeInt(cookie.getVersion());
        out.writeBoolean(cookie.getDiscard());
        out.writeBoolean(cookie.getSecure());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        clientCookie = new HttpCookie(name, value);
        clientCookie.setPath((String)in.readObject());
        clientCookie.setComment((String) in.readObject());
        clientCookie.setCommentURL((String) in.readObject());
        clientCookie.setDomain((String) in.readObject());
        clientCookie.setPortlist((String) in.readObject());
        clientCookie.setMaxAge(in.readLong());
        clientCookie.setVersion(in.readInt());
        clientCookie.setDiscard(in.readBoolean());
        clientCookie.setSecure(in.readBoolean());
    }
}