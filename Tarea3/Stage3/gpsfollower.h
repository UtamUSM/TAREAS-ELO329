#ifndef GPSFOLLOWER_H
#define GPSFOLLOWER_H

#include "subscriber.h"
#include <QWidget>

class QLabel;

class GPSFollower : public QWidget, public Subscriber {
    Q_OBJECT

public:
    GPSFollower(const QString& name, const QString& topicName, QWidget* parent = nullptr);
    void update(const QString& message) override;

private:
    QLabel* timeLabel;
    QLabel* xLabel;
    QLabel* yLabel;
};

#endif // GPSFOLLOWER_H
