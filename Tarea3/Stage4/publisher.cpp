#include "publisher.h"
#include "broker.h"
#include "topic.h"

Publisher::Publisher(const QString& name, Broker* broker, const QString& topicName)
    : Component(name, topicName)
{
    topic = broker->createTopic(topicName);
}

void Publisher::publishNewEvent(const QString& message) {
    if (topic) {
        topic->notify(message);
    }
}
