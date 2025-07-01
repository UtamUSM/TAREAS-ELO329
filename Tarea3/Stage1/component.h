#ifndef COMPONENT_H
#define COMPONENT_H

#include <QString>

class Component {
public:
    Component(const QString& componentName, const QString& topicName);

    QString getName() const;
    QString getTopicName() const;

protected:
    QString name;
    QString topicName;

    Component() = delete; // Prohíbe usar el constructor por defecto
};

#endif // COMPONENT_H
