package test;

public class Test {

    public static class LongTaskDemmo implements Command{

        String s;

        public LongTaskDemmo(String s) {
            this.s = s;
        }

        @Override
        public void execute() {
            try{
                Thread.sleep(2000); // 2 seconds...
            } catch (InterruptedException e) {}

            System.out.println(s + " finished");
        }
    }

    public static void main(String[] args) throws Exception{
        // Active Object, priority because the commands will be ordered by priority (0 - the highest priority)
        ActivePriorityController apc = new ActivePriorityController();
        // adding Commands to the queue
        apc.addCommand(new LongTaskDemmo("C"), 2);
        apc.addCommand(new LongTaskDemmo("B"), 1);
        apc.addCommand(new LongTaskDemmo("A"), 0);
        /** close() will safely stop the thread who runs the commands */
        apc.close();
        /** the main only puts the Commands in the queue, but doesn't execute them. so the main will be dead immediately.
         *  another thread will execute them, and he's the one who has to wait 2 seconds for each command to finish.
         *  WE SEPARATE BETWEEN THE THREAD WHO ADDS THE COMMANDS, AND THE THREAD WHO RUNS THEM */
        System.out.println("main is dead");
    }
}
