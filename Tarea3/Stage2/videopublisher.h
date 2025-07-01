#ifndef VIDEOPUBLISHER_H
#define VIDEOPUBLISHER_H

#include "publisher.h"
#include <QWidget>

class QLineEdit;

class VideoPublisher : public QWidget, public Publisher {
    Q_OBJECT

public:
    VideoPublisher(Broker* broker, const QString& name, const QString& topicName, QWidget* parent = nullptr);

private slots:
    void onUrlEntered();

private:
    QLineEdit* urlInput;
};

#endif // VIDEOPUBLISHER_H
