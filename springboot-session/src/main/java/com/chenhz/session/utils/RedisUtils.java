/**
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.chenhz.session.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOperations;
    @Resource(name = "redisTemplate")
    private ListOperations<String, Object> listOperations;
    @Resource(name = "redisTemplate")
    private SetOperations<String, Object> setOperations;
    @Resource(name = "redisTemplate")
    private ZSetOperations<String, Object> zSetOperations;
    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24L;
    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;


    public boolean isExist(String key){
        return redisTemplate.hasKey(key);
    }

    //模糊删除key
    public String fuzzyKey(String key) {
        Set<String> keySet = redisTemplate.keys(key + "*");
        Iterator iter = keySet.iterator();
        while (iter.hasNext()) {
            return iter.next().toString();
        }
        return "";
    }

    //模糊删除key
    public void fuzzyDelete(String key) {
        Set<String> keys = redisTemplate.keys(key + "*");
        redisTemplate.delete(keys);
    }

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public void putAllHash(String key, Map<String, Object> map) {
        putAllHash(key, map, DEFAULT_EXPIRE);
    }

    public void putAllHash(String key, Map<String, Object> map, long expire) {
        hashOperations.putAll(key, map);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public Object getZSet(String key, Object object) {
        return getZSet(key, object, NOT_EXPIRE);
    }

    public Set<Object> getZSet(String key, Object object, long expire) {
        Set<Object> result = zSetOperations.range(key, 0, -1);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return result;
    }

    public void putZSet(String key, Object object, double orderNum) {
        putZSet(key, object, orderNum, DEFAULT_EXPIRE);
    }

    public void putZSet(String key, Object object, double orderNum, long expire) {
        zSetOperations.add(key, object, orderNum);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public Map<String, Object> entries(String key) {
        return entries(key, NOT_EXPIRE);
    }

    public Map<String, Object> entries(String key, long expire) {
        Map<String, Object> map = hashOperations.entries(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return map;
    }

    public Boolean hasHash(String key, Object object) {
        return hasHash(key, object, NOT_EXPIRE);
    }

    public Boolean hasHash(String key, Object object, long expire) {
        Boolean result = hashOperations.hasKey(key, object.toString());
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return result;
    }

    public Object getHash(String key, Object object) {
        return getHash(key, object, NOT_EXPIRE);
    }

    public Object getHash(String key, Object object, long expire) {
        Object result = hashOperations.get(key, object.toString());
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return result;
    }


    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


    public Set<Object> getHashSet(String key) {
        return setOperations.members(key);
    }

    public void setHashSet(String key, Object value) {
        setOperations.add(key, value);
    }

    public void pushAllHashSet(String key, Set<Object> hashSet) {
        for (Object object : hashSet) {
            setHashSet(key, object);
        }
    }

    /**
     * 失效
     *
     * @param key
     */
    public void setExpire(String key) {
        redisTemplate.expire(key, -1, TimeUnit.SECONDS);
    }
}
