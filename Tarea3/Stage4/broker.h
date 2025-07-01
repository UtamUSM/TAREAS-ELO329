#ifndef BROKER_H
#define BROKER_H

#include <QString>
#include <QVector>

class Topic;
class Subscriber;

class Broker {
public:
    Broker();

    Topic* createTopic(const QString& topicName);
    bool subscribe(Subscriber* sub);

private:
    Topic* findTopic(const QString& topicName);

    QVector<Topic*> topics;
};

#endif // BROKER_H
