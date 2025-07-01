#ifndef VIDEOFOLLOWER_H
#define VIDEOFOLLOWER_H

#include "subscriber.h"
#include <QWidget>

class QPushButton;

class VideoFollower : public QWidget, public Subscriber {
    Q_OBJECT

public:
    VideoFollower(const QString& name, const QString& topicName, QWidget* parent = nullptr);

    void update(const QString& message) override;

private:
    QPushButton* button;
};

#endif // VIDEOFOLLOWER_H
