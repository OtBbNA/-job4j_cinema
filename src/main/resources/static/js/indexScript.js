const filmCard = document.querySelectorAll('.carousel__element');
const leftBtn = document.querySelector('.carousel__button__prev');
const rightBtn = document.querySelector('.carousel__button__next');

let index = filmCard.length - 1;

window.addEventListener('load', () => {
    filmCard[index].style.opacity = '1';
});

// Кнопки влево - вправо

leftBtn.addEventListener('click', () => {
    back();
});

rightBtn.addEventListener('click', () => {
    forvard();
});

// Реализация кнопок

function back () {
    filmCard[index].style.opacity = '0';
    index--;
    if (index < 0) {
        index = filmCard.length - 1;
    }
    filmCard[index].style.opacity = '1';
}

function forvard () {
    filmCard[index].style.opacity = '0';
    index++;
    if (index >= filmCard.length) {
        index = 0;
    }
    filmCard[index].style.opacity = '1';
}

// Автопрокрутка

window.setInterval(forvard, 5000);