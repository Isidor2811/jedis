package redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.JedisPooled;

public class RedisClient {

  static JedisPooled jedis;

  static {
    jedis = new JedisPooled("localhost", 6379);
  }

  // ********************************* STRING ***************************************

  /**
   * SET create new key-value pair, or override existed value if key already exists.
   */
  public static void setString(String key, String value) {
    jedis.set(key, value);
  }

  /**
   * GET value by key
   */
  public static String getString(String key) {
    return jedis.get(key);
  }

  /**
   * EXISTS return true if key exists in DB, and false if not
   */
  public static boolean isExists(String key) {
    return jedis.exists(key);
  }

  // ********************************* LIST ***************************************

  /**
   * RPUSH puts the new element at the end of the list.
   */
  public static void addToEndOfTheList(String key, String... list) {
    jedis.rpush(key, list);
  }

  /**
   * LPUSH puts the new element at the start of the list.
   */
  public static void addToStartOfTheList(String key, String... list) {
    jedis.lpush(key, list);
  }

  /**
   * LRANGE gives a subset of the list. It takes the index of the first element you want to retrieve
   * as its first parameter and the index of the last element you want to retrieve as its second
   * parameter. A value of -1 for the second parameter means to retrieve elements until the end of
   * the list and -2 means to include up to the penultimate, and so forth.
   */
  public static List<String> getFromList(String key, int from, int to) {
    return jedis.lrange(key, from, to);
  }

  /**
   * LPOP removes the first element from the list and returns it.
   */
  public static String getElementFromStartOfTheList(String key) {
    return jedis.lpop(key);
  }

  /**
   * RPOP removes the last element from the list and returns it.
   */
  public static String getElementFromEndOfTheList(String key) {
    return jedis.rpop(key);
  }

  /**
   * LLEN returns size of the list
   */
  public static long getListSize(String key) {
    return jedis.llen(key);
  }

  // ********************************* SET ***************************************

  /**
   * SADD adds the given member to the set The return value of SADD: if the element we try to add is
   * already inside, then 0 is returned, otherwise SADD returns 1:
   */
  public static long addToSet(String key, String... values) {
    return jedis.sadd(key, values);
  }

  /**
   * SMEMBERS returns a list of all the members of this set.
   */
  public static Set<String> getFromSet(String key) {
    return jedis.smembers(key);
  }

  /**
   * SREM removes the given member from the set, returning 1 or 0 to signal if the member was
   * actually there or not.
   */
  public static boolean removeFromSet(String key, String... values) {
    return jedis.srem(key, values) == 1;
  }

  /**
   * SISMEMBER check if the given value is in the set
   */
  public static boolean isElementInSet(String key, String value) {
    return jedis.sismember(key, value);
  }

  /**
   * SUNION combines two or more sets and returns the list of all elements.
   */
  public static Set<String> combineSetsAndGetValues(String... keys) {
    return jedis.sunion(keys);
  }

  /**
   * The argument of SPOP after the name of the key, is the number of elements we want it to return,
   * and remove from the set.
   */
  public static Set<String> getFromSet(String key, int count) {
    return jedis.spop(key, count);
  }

  /**
   * SRANDMEMBER return random elements without removing such elements from the set
   */
  public static List<String> getRandomElementsFromSet(String key, int count) {
    return jedis.srandmember(key, count);
  }

  // ****************************** SORTED SET ************************************

  /**
   * ZADD add to sorted set, where score is value by which will be sorting
   */
  public static long addToSortedSet(String key, double score, String member) {
    return jedis.zadd(key, score, member);
  }

  /**
   * ZRANGE get values from sorted set in range
   */
  public static List<String> getFromSortedSet(String key, int from, int to) {
    return jedis.zrange(key, from, to);
  }

  // ****************************** HASH ************************************

  /**
   * HSET add data to hash
   */
  public static long addToHash(String key, Map<String, String> value) {
    return jedis.hset(key, value);
  }

  /**
   * HGETALL - to get back the saved data
   */
  public static Map<String, String> getAllFromHash(String key) {
    return jedis.hgetAll(key);
  }

  /**
   * HGET - get specific field value from hash
   */
  public static String getSpecificFieldFromHash(String key, String field) {
    return jedis.hget(key, field);
  }

  /**
   * HMGET - get multiple fields from hash
   */
  public static List<String> getMultipleFieldsFromHash(String key, String... fields) {
    return jedis.hmget(key, fields);
  }

}
