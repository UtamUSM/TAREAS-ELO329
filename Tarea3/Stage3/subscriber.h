#ifndef SUBSCRIBER_H
#define SUBSCRIBER_H

#include "component.h"

class Subscriber : public Component {
public:
    Subscriber(const QString& name, const QString& topicName);

    virtual void update(const QString& message) = 0; // método puro = clase abstracta

protected:
    Subscriber() = delete; // bloquea el constructor sin argumentos
};

#endif // SUBSCRIBER_H
