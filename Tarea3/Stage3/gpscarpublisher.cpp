#include "gpscarpublisher.h"
#include "broker.h"

#include <QVBoxLayout>
#include <QFileDialog>
#include <QFile>
#include <QTextStream>
#include <QLabel>
#include <QDebug>

GPSCarPublisher::GPSCarPublisher(Broker* broker, const QString& name, const QString& topicName, QWidget* parent)
    : QWidget(parent),
    Publisher(name, broker, topicName),
    timer(new QTimer(this)),
    currentTime(0.0)
{
    QVBoxLayout* layout = new QVBoxLayout(this);
    QLabel* label = new QLabel("Publicador GPS: " + name);
    layout->addWidget(label);
    this->setLayout(layout);

    loadPositionsFromFile();

    if (!positions.isEmpty()) {
        connect(timer, &QTimer::timeout, this, &GPSCarPublisher::publishNext);
        timer->start(1000); // cada 1 segundo
    }
}

void GPSCarPublisher::loadPositionsFromFile() {
    QString fileName = QFileDialog::getOpenFileName(this, "Seleccionar archivo de posiciones", "", "Text Files (*.txt)");
    if (fileName.isEmpty()) return;

    QFile file(fileName);
    if (!file.open(QIODevice::ReadOnly | QIODevice::Text)) return;

    QTextStream in(&file);
    while (!in.atEnd()) {
        QString line = in.readLine();
        QStringList parts = line.split(' ', Qt::SkipEmptyParts);
        if (parts.size() != 3) continue;

        Position pos;
        pos.time = parts[0].toDouble();
        pos.x = parts[1].toDouble();
        pos.y = parts[2].toDouble();
        positions.append(pos);
    }

    std::sort(positions.begin(), positions.end(), [](const Position& a, const Position& b) {
        return a.time < b.time;
    });
}

void GPSCarPublisher::publishNext() {
    if (positions.isEmpty()) return;

    currentTime += 1.0;

    Position interpolated = interpolate(currentTime);

    QString message = QString::number(currentTime, 'f', 0) + " "
                      + QString::number(interpolated.x, 'f', 2) + " "
                      + QString::number(interpolated.y, 'f', 2);

    publishNewEvent(message);

    if (currentTime > positions.last().time)
        timer->stop();
}

Position GPSCarPublisher::interpolate(double t) {
    for (int i = 0; i < positions.size() - 1; ++i) {
        Position p1 = positions[i];
        Position p2 = positions[i + 1];

        if (t >= p1.time && t <= p2.time) {
            double ratio = (t - p1.time) / (p2.time - p1.time);
            return {
                t,
                p1.x + ratio * (p2.x - p1.x),
                p1.y + ratio * (p2.y - p1.y)
            };
        }
    }
    return positions.last(); // si pasa el final
}
