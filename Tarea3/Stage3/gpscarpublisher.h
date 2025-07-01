#ifndef GPSCARPUBLISHER_H
#define GPSCARPUBLISHER_H

#include "publisher.h"
#include <QWidget>
#include <QVector>
#include <QTimer>

struct Position {
    double time;
    double x;
    double y;
};

class QLabel;

class GPSCarPublisher : public QWidget, public Publisher {
    Q_OBJECT

public:
    GPSCarPublisher(Broker* broker, const QString& name, const QString& topicName, QWidget* parent = nullptr);

private slots:
    void publishNext();

private:
    void loadPositionsFromFile();
    Position interpolate(double targetTime);

    QVector<Position> positions;
    QTimer* timer;
    double currentTime;
};

#endif // GPSCARPUBLISHER_H
