# java-kanban
Repository for homework project.
Постарался создать структуру проекта по пакетам, приближенную к реальной(по моему представлению) и максимально приблизить код к функциональности API приложения.

Расставил модификаторы доступа, всё реализовано с помощью геттеров и сеттеров, иначе доступа к защищённым переменным нет.

Не баг, а фича:D - Вместе с эпиком, удаляются и подзадачи, которые к нему относились.

После удаления последней подазачи, эпик приобретает статус NEW.
Статус эпика рассчитывается после изменения подзадачи, включая удаление. 

