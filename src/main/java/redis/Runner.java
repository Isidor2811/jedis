package redis;

import com.github.javafaker.Faker;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Runner {

  private static final Faker faker = new Faker();

  public static void main(String[] args) {

    // **************************** STRING *******************************************

    String key = "user_id:" + faker.internet().uuid();
    //set string
    RedisClient.setString(key, faker.internet().ipV4Address());
    //get string
    System.out.println(RedisClient.getString(key));
    //check if key exist
    System.out.println(RedisClient.isExists(key));
    System.out.println(RedisClient.isExists(faker.internet().uuid()));

    // **************************** LIST *******************************************
    String listName = faker.internet().uuid();
    String user1 = "Ben";
    String user2 = "Jack";
    RedisClient.addToEndOfTheList(listName, user1, user2); //Ben -> Jack
    String user3 = "William";
    RedisClient.addToEndOfTheList(listName, user3); //Ben -> Jack -> William

    //add at start of the list
    String user4 = "Anna";
    RedisClient.addToStartOfTheList(listName, user4); //Anna -> Ben -> Jack -> William

    //get data from list
    //Anna -> Ben -> Jack -> William
    List<String> dataFromList = RedisClient.getFromList(listName, 0, -1);
    System.out.println(dataFromList);

    //get and remove element from the start of the list
    String first = RedisClient.getElementFromStartOfTheList(listName);
    System.out.println(first); //Anna

    //get and remove element from the end of the list
    String last = RedisClient.getElementFromEndOfTheList(listName);
    System.out.println(last); //William

    //get list size
    long listSize = RedisClient.getListSize(listName);
    System.out.println(listSize); //2

    // **************************** SET *******************************************
    String setName = faker.internet().uuid();
    String worker1 = "Alan";
    String worker2 = "Max";
    String worker3 = "Alan";

    //add to set
    RedisClient.addToSet(setName, worker1, worker2, worker3);
    //get value from set
    Set<String> values = RedisClient.getFromSet(setName);
    System.out.println(values); //Alan, Max

    //remove from set
    boolean isRemoved = RedisClient.removeFromSet(setName, "Alan");
    System.out.println(isRemoved); //true
    isRemoved = RedisClient.removeFromSet(setName, "Brian");
    System.out.println(isRemoved); //false

    //check if element in set
    boolean isElementInSet = RedisClient.isElementInSet(setName, "Max");
    System.out.println(isElementInSet);//true

    //combine sets
    String firstSetName = faker.internet().uuid();
    String secondSetName = faker.internet().uuid();
    RedisClient.addToSet(firstSetName, "a", "b", "c");
    RedisClient.addToSet(secondSetName, "d", "e", "f");
    Set<String> all = RedisClient.combineSetsAndGetValues(firstSetName, secondSetName);
    System.out.println(all); //a,b,c,d,e,f

    //get from set
    Set<String> valuesFromSet = RedisClient.getFromSet(firstSetName, 2);
    System.out.println(valuesFromSet); //a,b or b,c or a,c ... ect
    Set<String> notRemovedElements = RedisClient.getFromSet(firstSetName);
    System.out.println(notRemovedElements);

    //get random element from set
    List<String> randomElementsFromSet = RedisClient.getRandomElementsFromSet(secondSetName, 2);
    System.out.println(randomElementsFromSet);

    // ************************** SORTED SET ***************************************

    //add to sorted set
    String sortedSetName = faker.internet().uuid();
    RedisClient.addToSortedSet(sortedSetName, 1, "A");
    RedisClient.addToSortedSet(sortedSetName, 4, "B");
    RedisClient.addToSortedSet(sortedSetName, 2, "C");
    RedisClient.addToSortedSet(sortedSetName, 3, "E");

    //get from sorted set
    List<String> sortedData = RedisClient.getFromSortedSet(sortedSetName, 0, -1);
    System.out.println(sortedData);

    // ****************************** HASH ************************************

    //add to hash
    String hashName = faker.internet().uuid();
    Map<String, String> userData = new HashMap<>();
    userData.put("firstName", faker.name().firstName());
    userData.put("lastName", faker.name().lastName());
    userData.put("fullAddress", faker.address().fullAddress());
    userData.put("age", String.valueOf(faker.number().randomNumber(2, true)));

    RedisClient.addToHash(hashName, userData);

    //get all
    Map<String, String> allFromHash = RedisClient.getAllFromHash(hashName);
    System.out.println(allFromHash);

    //get specific field
    String age = RedisClient.getSpecificFieldFromHash(hashName, "age");
    System.out.println(age);

    //get multiple fields
    List<String> multipleFields = RedisClient.getMultipleFieldsFromHash(hashName, "age",
        "firstName");
    System.out.println(multipleFields);

  }
}
