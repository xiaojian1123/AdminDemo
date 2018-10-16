let Point = function (x, y) {
    this.x = x;
    this.y = y;
};

let Circle = function (rad, centerPoint) {
    this.rad = rad;
    this.centerPoint = centerPoint;
};

let DelaunayDataSet = function (vertex, context) {
    this.vertex = vertex;
    this.context = context;

    this.fillTriangleColor = "#ff0000";
    this.fillTriangleCheck = true;

    this.strokeTriangleColor = "rgba( 255, 255, 255, 0)";
    this.strokeTriangleCheck = true;
};

let TriSettings = [];
let lights = [];
let max_lights = 50;

DelaunayDataSet.prototype.drawLight = function () {
    while (lights.length < max_lights) {
        let light = {
            x: Math.random() * this.context.canvas.width,
            y: Math.random() * this.context.canvas.height,
            angle: Math.random() * 360 * (Math.PI / 180),
            speed: Math.random() * 10
        };
        lights.push(light);
    }
    for (let i = 0; i < lights.length; i++) {
        let light = lights[i];
        light.x += Math.cos(light.angle) * light.speed;
        light.y += Math.sin(light.angle) * light.speed;
        if (light.x < 0 || light.y < 0 || light.x > this.context.canvas.width || light.y > this.context.canvas.height) {
            light.x = Math.random() * this.context.canvas.width;
            light.y = Math.random() * this.context.canvas.height;
            continue;
        }
        this.context.fillStyle = "#96c";
        this.context.beginPath();
        this.context.arc(light.x, light.y, 5, 0, 2 * Math.PI, false);
        this.context.shadowColor = '#96c';
        this.context.shadowBlur = 30;
        this.context.shadowOffsetX = 0;
        this.context.shadowOffsetY = 0;
        this.context.fill();
        this.context.closePath();
    }
    this.context.shadowBlur = 0;
};

DelaunayDataSet.prototype.drawTriangle = function () {

    for (let i = 0; i < this.triangleVertexNumber.length; i += 3) {

        if (this.triangleVertexNumber[i] !== 0 && this.triangleVertexNumber[i] !== 1 && this.triangleVertexNumber[i] !== 2 && this.triangleVertexNumber[i + 1] !== 0 && this.triangleVertexNumber[i + 1] !== 1 && this.triangleVertexNumber[i + 1] !== 2 && this.triangleVertexNumber[i + 2] !== 0 && this.triangleVertexNumber[i + 2] !== 1 && this.triangleVertexNumber[i + 2] !== 2) {

            let ctx = this.context;
            let points = [];
            points.push({
                x: this.vertex[this.triangleVertexNumber[i]].x,
                y: this.vertex[this.triangleVertexNumber[i]].y
            });
            points.push({
                x: this.vertex[this.triangleVertexNumber[i + 1]].x,
                y: this.vertex[this.triangleVertexNumber[i + 1]].y
            });
            points.push({
                x: this.vertex[this.triangleVertexNumber[i + 2]].x,
                y: this.vertex[this.triangleVertexNumber[i + 2]].y
            });

            ctx.beginPath();
            ctx.moveTo(points[0].x, points[0].y);
            ctx.lineTo(points[1].x, points[1].y);
            ctx.lineTo(points[2].x, points[2].y);
            ctx.lineTo(points[0].x, points[0].y);

            if (this.fillTriangleCheck) {
                let settings = TriSettings[i];
                if (!settings) {
                    let num = 120 + (80 / this.triangleVertexNumber.length * i) | 0;
                    let sp = new Point(this.vertex[this.triangleVertexNumber[i]].x, this.vertex[this.triangleVertexNumber[i]].y);
                    let ep = new Point(sp.x + 50 + Math.random() * 200, sp.y + 50 + Math.random() * 200);
                    let opacity = .05 + Math.random() * .2;
                    settings = {
                        start_point: sp,
                        end_point: ep,
                        opacity: opacity
                    };
                    TriSettings[i] = settings;
                }

                let color = '70,70,70';
                let collision = false;
                for (let l = 0; l < lights.length; l++) {
                    let collision = is_in_triangle(lights[l].x, lights[l].y, points[0].x, points[0].y, points[1].x, points[1].y, points[2].x, points[2].y);
                    if (collision) {
                        color = '153,102,204';
                        continue;
                    }
                }

                let grad = ctx.createLinearGradient(settings.start_point.x, settings.start_point.y, settings.end_point.x, settings.end_point.y);
                grad.addColorStop(0, 'rgba(0,0,0,' + settings.opacity + ')');
                grad.addColorStop(1, 'rgba(' + color + ',' + settings.opacity + ')');
                ctx.fillStyle = grad;
                ctx.fill();
            }

            if (this.strokeTriangleCheck) {
                ctx.strokeStyle = this.strokeTriangleColor;
                ctx.stroke();
            }

            ctx.closePath();

        }
    }
};

DelaunayDataSet.prototype.update = function () {

    let vertexNumber = this.vertex.length;
    this.triangleVertexNumber = [0, 1, 2];
    this.circumCircles = [];


    let firstCircle = calculationCircle(this.vertex[0], this.vertex[1], this.vertex[2]);
    this.circumCircles.push(firstCircle);

    for (let i = 3; i < vertexNumber; i++) {
        calTriangles(this, i);
        if (i > 3) {
            removeTriangle(this, i);
        }
    }

};

function distanceBetweenPointAndCircle(pt, circle) {
    let dx = pt.x - circle.centerPoint.x;
    let dy = pt.y - circle.centerPoint.y;

    return Math.sqrt(dx * dx + dy * dy);
}

function judgeBetweenDistance(_pt, _circle) {
    let dis = distanceBetweenPointAndCircle(_pt, _circle);

    let circleJudge = false;
    if (dis < _circle.rad) {
        circleJudge = true;
    }

    return circleJudge;
}

function calTriangles(_delaunayDataSet, num) {
    let newNumber = num;
    let pt = _delaunayDataSet.vertex[newNumber];

    let tempVertexNumber = [];
    let tempCircles = [];
    let tempNumbers = [];

    for (let i = 0; i < _delaunayDataSet.circumCircles.length; i++) {
        if (judgeBetweenDistance(pt, _delaunayDataSet.circumCircles[i])) {
            tempNumbers.push(i);

            let selectingNum01 = _delaunayDataSet.triangleVertexNumber[3 * i];
            let selectingNum02 = _delaunayDataSet.triangleVertexNumber[3 * i + 1];
            let selectingNum03 = _delaunayDataSet.triangleVertexNumber[3 * i + 2];

            tempVertexNumber.push(selectingNum01);
            tempVertexNumber.push(selectingNum02);
            tempVertexNumber.push(newNumber);

            tempVertexNumber.push(selectingNum02);
            tempVertexNumber.push(selectingNum03);
            tempVertexNumber.push(newNumber);

            tempVertexNumber.push(selectingNum03);
            tempVertexNumber.push(selectingNum01);
            tempVertexNumber.push(newNumber);

            let ct01circle1 = calculationCircle(_delaunayDataSet.vertex[selectingNum01], _delaunayDataSet.vertex[selectingNum02], _delaunayDataSet.vertex[newNumber]);
            let ct01circle2 = calculationCircle(_delaunayDataSet.vertex[selectingNum02], _delaunayDataSet.vertex[selectingNum03], _delaunayDataSet.vertex[newNumber]);
            let ct01circle3 = calculationCircle(_delaunayDataSet.vertex[selectingNum03], _delaunayDataSet.vertex[selectingNum01], _delaunayDataSet.vertex[newNumber]);

            tempCircles.push(ct01circle1);
            tempCircles.push(ct01circle2);
            tempCircles.push(ct01circle3);
        }
    }

    for (let i = 0; i < tempVertexNumber.length; i++) {
        _delaunayDataSet.triangleVertexNumber.push(tempVertexNumber[i]);
    }

    for (let i = 0; i < tempCircles.length; i++) {
        _delaunayDataSet.circumCircles.push(tempCircles[i]);
    }

    for (let i = 0; i < tempNumbers.length; i++) {
        let num = tempNumbers[i] - i;

        let slicedObjectPtNumbers;
        let slicedCircles;

        if (num === 0) {
            slicedObjectPtNumbers = _delaunayDataSet.triangleVertexNumber.slice(3);
            slicedCircles = _delaunayDataSet.circumCircles.slice(1);
        } else {
            let slicedObjectPtNumberBefore = _delaunayDataSet.triangleVertexNumber.slice(0, 3 * num);
            let slicedObjectPtNumberAfter = _delaunayDataSet.triangleVertexNumber.slice(3 * num + 3);
            slicedObjectPtNumbers = slicedObjectPtNumberBefore.concat(slicedObjectPtNumberAfter);

            let slicedCircleBefore = _delaunayDataSet.circumCircles.slice(0, num);
            let slicedCircleAfter = _delaunayDataSet.circumCircles.slice(1 + num);
            slicedCircles = slicedCircleBefore.concat(slicedCircleAfter);
        }

        _delaunayDataSet.triangleVertexNumber = slicedObjectPtNumbers;
        _delaunayDataSet.circumCircles = slicedCircles;
    }

}

function calculationCircle(pt01, pt02, pt03) {

    let x1 = pt01.x;
    let y1 = pt01.y;

    let x2 = pt02.x;
    let y2 = pt02.y;

    let x3 = pt03.x;
    let y3 = pt03.y;

    let c = 2.0 * ((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1));
    let tempX = ((y3 - y1) * (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1) + (y1 - y2) * (x3 * x3 - x1 * x1 + y3 * y3 - y1 * y1)) / c;
    let tempY = ((x1 - x3) * (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1) + (x2 - x1) * (x3 * x3 - x1 * x1 + y3 * y3 - y1 * y1)) / c;
    let tempPt = new Point(tempX, tempY);

    let tempRad = Math.sqrt(Math.pow(tempX - x1, 2) + Math.pow(tempY - y1, 2));

    return new Circle(tempRad, tempPt);
}

function removeTriangle(_delaunayDataSet, tempVertexNum) {
    let circumcircleArrays = _delaunayDataSet.circumCircles;
    let ommitCircumCircleNumbers = [];

    for (let i = 0; i < circumcircleArrays.length; i++) {
        let vertexNum01 = _delaunayDataSet.triangleVertexNumber[i * 3];
        let vertexNum02 = _delaunayDataSet.triangleVertexNumber[i * 3 + 1];
        let vertexNum03 = _delaunayDataSet.triangleVertexNumber[i * 3 + 2];

        for (let num = 0; num < tempVertexNum; num++) {
            if (num !== vertexNum01 && num !== vertexNum02 && num !== vertexNum03) {

                if (judgeBetweenDistance(_delaunayDataSet.vertex[num], circumcircleArrays[i])) {
                    ommitCircumCircleNumbers.push(i);
                    break;
                }

            }
        }

    }


    //omit
    let tempCircumCircleArray = [];
    let tempTriagneNumberArray = [];

    for (let i = 0; i < circumcircleArrays.length; i++) {
        let j = 0;
        for (; j < ommitCircumCircleNumbers.length; j++) {
            if (ommitCircumCircleNumbers[j] === i) {
                break;
            }
        }

        if (j === ommitCircumCircleNumbers.length) {

            tempTriagneNumberArray.push(_delaunayDataSet.triangleVertexNumber[3 * i]);
            tempTriagneNumberArray.push(_delaunayDataSet.triangleVertexNumber[3 * i + 1]);
            tempTriagneNumberArray.push(_delaunayDataSet.triangleVertexNumber[3 * i + 2]);

            tempCircumCircleArray.push(_delaunayDataSet.circumCircles[i]);
        }
    }

    _delaunayDataSet.triangleVertexNumber = [];
    for (let i = 0; i < tempTriagneNumberArray.length; i++) {
        _delaunayDataSet.triangleVertexNumber[i] = tempTriagneNumberArray[i];
    }

    _delaunayDataSet.circumCircles = [];
    for (let i = 0; i < tempCircumCircleArray.length; i++) {
        _delaunayDataSet.circumCircles[i] = tempCircumCircleArray[i];
    }
}

function initTriangle(context, recWid, recHig, recTop, recLeft) {
    let vertex = [];

    let bigRad = Math.sqrt(Math.pow(recWid, 2) + Math.pow(recHig, 2)) / 2;
    let bigCirclePos = new Point(recWid / 2 + recLeft, recHig / 2 + recTop);

    vertex.push(new Point(bigCirclePos.x - Math.sqrt(3) * bigRad, bigCirclePos.y - bigRad));
    vertex.push(new Point(bigCirclePos.x + Math.sqrt(3) * bigRad, bigCirclePos.y - bigRad));
    vertex.push(new Point(bigCirclePos.x, bigCirclePos.y + bigRad * 2));

    return new DelaunayDataSet(vertex, context);
}

function is_in_triangle(px, py, ax, ay, bx, by, cx, cy) {

    let v0 = [cx - ax, cy - ay];
    let v1 = [bx - ax, by - ay];
    let v2 = [px - ax, py - ay];

    let dot00 = (v0[0] * v0[0]) + (v0[1] * v0[1]);
    let dot01 = (v0[0] * v1[0]) + (v0[1] * v1[1]);
    let dot02 = (v0[0] * v2[0]) + (v0[1] * v2[1]);
    let dot11 = (v1[0] * v1[0]) + (v1[1] * v1[1]);
    let dot12 = (v1[0] * v2[0]) + (v1[1] * v2[1]);

    let invDenom = 1 / (dot00 * dot11 - dot01 * dot01);

    let u = (dot11 * dot02 - dot01 * dot12) * invDenom;
    let v = (dot00 * dot12 - dot01 * dot02) * invDenom;

    return ((u >= 0) && (v >= 0) && (u + v < 1));
}

window.requestAnimFrame = (function () {
    return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
        function (callback) {
            window.setTimeout(callback, 1000 / 60);
        };
})();

export  default {
    show: function () {
        let canvas = document.getElementById("canvas");
        let canvasWid = window.innerWidth - 20;
        let canvasHig = window.innerHeight - 20;

        canvas.width = canvasWid;
        canvas.height = canvasHig;

        let context = canvas.getContext("2d");

        let recWid = canvasWid;
        let recHig = canvasHig;

        let recTop = 0;
        let recLeft = 0;

        let delaneyNum = 80;
        let myDelaunayDataSet = initTriangle(context, recWid, recHig, recTop, recLeft);

        let tempPt;
        tempPt = new Point(0, 0);
        myDelaunayDataSet.vertex.push(tempPt);

        tempPt = new Point(canvasWid, 0);
        myDelaunayDataSet.vertex.push(tempPt);

        tempPt = new Point(0, canvasHig);
        myDelaunayDataSet.vertex.push(tempPt);

        tempPt = new Point(canvasWid, canvasHig);
        myDelaunayDataSet.vertex.push(tempPt);

        for (let i = 0; i < delaneyNum - 4; i++) {
            let pt = new Point(Math.random() * recWid + recLeft, Math.random() * recHig + recTop);
            myDelaunayDataSet.vertex.push(pt);
        }

        let mousePos = new Point(canvasWid / 2, canvasHig / 2);
        myDelaunayDataSet.vertex.push(mousePos);

        myDelaunayDataSet.update();
        myDelaunayDataSet.drawTriangle();

        loop();

        function loop() {
            context.clearRect(0, 0, canvasWid, canvasHig);
            myDelaunayDataSet.drawTriangle();
            myDelaunayDataSet.drawLight();
            requestAnimFrame(loop);
        }

    }
}
