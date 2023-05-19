package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.JunctionDetection;
import org.firstinspires.ftc.teamcode.vision.TestVision;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous
public class VisionTest extends LinearOpMode {
    JunctionDetection pipeline = new JunctionDetection();
    @Override
    public void runOpMode() throws InterruptedException {
        int pos = -1;
        // init started
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        camera.setPipeline(pipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1600, 1200, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
        while(!isStopRequested() && opModeInInit()){
            // init loop
            if(pipeline.pos != -1) {
                pos = pipeline.pos;
                telemetry.addData("Pos", pos);
            }
            telemetry.update();
        }
        // Op Mode Started
        telemetry.addData("Pos recognized", pos);
        telemetry.update();
        while(!isStopRequested()){}
    }
}
