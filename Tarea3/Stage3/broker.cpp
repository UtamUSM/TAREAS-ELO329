#include "broker.h"
#include "topic.h"
#include "subscriber.h"

Broker::Broker() {
    // Constructor vacío, inicialización de topics ya está en el campo
}

Topic* Broker::createTopic(const QString& topicName) {
    Topic* topic = findTopic(topicName);
    if (!topic) {
        topic = new Topic(topicName);
        topics.append(topic);
    }
    return topic;
}

bool Broker::subscribe(Subscriber* sub) {
    QString topicName = sub->getTopicName();
    Topic* topic = findTopic(topicName);
    if (topic) {
        topic->subscribe(sub);
        return true;
    } else {
        return false; // tópico no existe
    }
}

Topic* Broker::findTopic(const QString& topicName) {
    for (Topic* topic : topics) {
        if (topic->hasThisName(topicName)) {
            return topic;
        }
    }
    return nullptr;
}
