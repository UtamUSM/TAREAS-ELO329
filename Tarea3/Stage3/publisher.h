#ifndef PUBLISHER_H
#define PUBLISHER_H

#include "component.h"

class Topic;
class Broker;

class Publisher : public Component {
public:
    Publisher(const QString& name, Broker* broker, const QString& topicName);

protected:
    void publishNewEvent(const QString& message);

private:
    Topic* topic;
};

#endif // PUBLISHER_H
