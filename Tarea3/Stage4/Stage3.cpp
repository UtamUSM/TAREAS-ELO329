#include "mainwindow.h"
#include <QApplication>

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);
    MainWindow w;
    w.setWindowTitle("Simulador Domótico - Etapa 1");
    w.resize(1000, 600);
    w.show();
    return app.exec();
}
