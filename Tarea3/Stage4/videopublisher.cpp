#include "videopublisher.h"
#include "broker.h"
#include <QVBoxLayout>
#include <QLineEdit>

VideoPublisher::VideoPublisher(Broker* broker, const QString& name, const QString& topicName, QWidget* parent)
    : QWidget(parent),
    Publisher(name, broker, topicName)
{
    QVBoxLayout* layout = new QVBoxLayout(this);

    urlInput = new QLineEdit(this);
    urlInput->setPlaceholderText("Ingrese URL de video y presione Enter");

    layout->addWidget(urlInput);
    this->setLayout(layout);

    connect(urlInput, &QLineEdit::returnPressed, this, &VideoPublisher::onUrlEntered);
}

void VideoPublisher::onUrlEntered() {
    QString url = urlInput->text();
    if (!url.isEmpty()) {
        publishNewEvent(url);
        urlInput->clear();
    }
}
