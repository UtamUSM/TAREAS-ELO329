#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

class QVBoxLayout;
class QHBoxLayout;
class Broker;
class QWidget;

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow {
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void createVideoPublisher();
    void createVideoFollower();
    void createGpsPublisher();
    void createGpsFollower();

private:
    Ui::MainWindow *ui;
    QWidget* centralWidgetContainer;

    Broker* broker;

    QVBoxLayout* publisherLayout;
    QVBoxLayout* subscriberLayout;

    QString promptFor(const QString& title, const QString& label);
};

#endif // MAINWINDOW_H
