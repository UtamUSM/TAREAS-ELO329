import java.util.ArrayList;

public class Broker {
   public Broker() {
      topics = new ArrayList<Topic>();
   }
   public Topic createTopic (String topicName){
      //....................................................................
      Topic topic =new Topic(topicName);  
      topics.add(topic);
      //....................................................................
      return topic;
   }
   public boolean subscribe (Subscriber sub){
      //....................................................................
      Topic topic=createTopic(sub.topicName);
      String topicName=sub.topicName;
      //....................................................................
      if ((topic=findTopic(topicName))!=null) {
         topic.subscribe(sub);
         return true;
      } else 
         return false; // topic does not exist.
   }
   private Topic findTopic (String topicName) {
      //....................................................................
      for (Topic p : topics){
         if(p.hasThisName(topicName)){
            return p;
         }
      }
      //....................................................................
      return null;  // topic not found
   }
   private ArrayList<Topic> topics;
}