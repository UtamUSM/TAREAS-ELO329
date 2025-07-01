#include "videofollower.h"
#include <QPushButton>
#include <QVBoxLayout>

VideoFollower::VideoFollower(const QString& name, const QString& topicName, QWidget* parent)
    : QWidget(parent), Subscriber(name, topicName)
{
    QVBoxLayout* layout = new QVBoxLayout(this);

    button = new QPushButton("Sin URL", this);
    button->setEnabled(false); // desactivado en Etapa 1

    layout->addWidget(button);
}

void VideoFollower::update(const QString& message) {
    button->setText(message);
}
