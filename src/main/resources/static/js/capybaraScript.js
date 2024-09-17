const bigCapy = document.querySelector('.moved__img');
let x = Math.floor(Math.random() * (((window.screen.width - (window.screen.width * 0.2)) - (window.screen.width * 0.15)))) + (window.screen.width * 0.15);
let y = Math.floor(Math.random() * ((window.screen.height * 0.5)));
console.log()
let rotate = 0;
let xdir = 1;
let ydir = 0;
let sw = 0;
let click = 1;
let moveInterval;
let shakingInterval;
let directionInterval;

window.addEventListener('load', () => {
    animation();
});

function animation () {
    moveInterval = window.setInterval(move, 42);
    shakingInterval = window.setInterval(shaking, 550);
    directionInterval = window.setInterval(direction, 3000);
}

// Анимации и перемещение

function move () {
    x += xdir;
    y += ydir;
    bigCapy.style.left = x + 'px';
    bigCapy.style.top = y + 'px';
    bigCapy.style.transform = 'rotate(' + rotate + 'deg)' + 'scale(' + xdir + ', 1)';
}

function direction () {
    (Math.random() < 0.5) ? xdir = -1 : xdir = 1;
    (Math.random() < 0.5) ? ydir = -1 : ydir = 1;
    rotCoef = 0;
    mirrorCoef = 0;
    if (ydir > 0 && xdir > 0) {
        rotCoef = 1;
        mirrorCoef = 0;
    } else if (ydir < 0 && xdir > 0) {
        rotCoef = -1;
        mirrorCoef = 0;
    } else if (ydir > 0 && xdir < 0) {
        rotCoef = 1;
        mirrorCoef = -45;
    } else if (ydir < 0 && xdir < 0) {
        rotCoef = -1;
        mirrorCoef = 45;
    }
    let rotateDir = Math.random() * 35 * rotCoef + mirrorCoef;
    rotate = rotateDir;

}

function shaking () {
    if (sw) {
        bigCapy.src = /*[[@{/img/move22.svg}]]*/ '/img/move22.svg';
        sw = 0;
    } else {
        bigCapy.src = /*[[@{/img/move12.svg}]]*/ '/img/move12.svg';
        sw = 1;
    }
}

// События мыши

bigCapy.onmousedown = function(e) {
    clearInterval(moveInterval);
    clearInterval(shakingInterval);
    clearInterval(directionInterval);

    moveAt(e);

    function moveAt(e) {
        x = e.pageX - 50;
        y = e.pageY - 20;
        bigCapy.style.left = x + 'px';
        bigCapy.style.top = y  + 'px';
        bigCapy.src = /*[[@{/img/lock.svg}]]*/ '/img/lock.svg';
        bigCapy.style.cursor = 'grab';
        bigCapy.style.transform = 'rotate(' + 0 + 'deg)';
    }

    document.onmousemove = function(e) {
        moveAt(e);
    };

    bigCapy.onmouseup = function() {
        document.onmousemove = null;
        bigCapy.src = /*[[@{/img/move12.svg}]]*/ '/img/move12.svg';
        animation();
        bigCapy.onmouseup = null;
    };
}

bigCapy.ondragstart = function() {
    return false;
};