#include "component.h"

Component::Component(const QString& componentName, const QString& topicName)
    : name(componentName), topicName(topicName)
{
}

QString Component::getName() const {
    return name;
}

QString Component::getTopicName() const {
    return topicName;
}
