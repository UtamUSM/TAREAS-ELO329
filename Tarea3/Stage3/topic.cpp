#include "topic.h"
#include "subscriber.h"

Topic::Topic(const QString& topicName)
    : topicName(topicName)
{
}

void Topic::subscribe(Subscriber* sub) {
    subscribers.append(sub);
}

void Topic::notify(const QString& message) {
    for (Subscriber* subscriber : subscribers) {
        subscriber->update(message);
    }
}

bool Topic::hasThisName(const QString& name) const {
    return topicName == name;
}
