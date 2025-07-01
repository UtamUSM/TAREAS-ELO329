#ifndef TOPIC_H
#define TOPIC_H

#include <QString>
#include <QVector>

class Subscriber;

class Topic {
public:
    explicit Topic(const QString& topicName);

    void subscribe(Subscriber* sub);
    void notify(const QString& message);
    bool hasThisName(const QString& name) const;

private:
    QString topicName;
    QVector<Subscriber*> subscribers;
};

#endif // TOPIC_H
