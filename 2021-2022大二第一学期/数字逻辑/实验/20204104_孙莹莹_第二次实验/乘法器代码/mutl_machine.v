`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/11 18:57:13
// Design Name: 
// Module Name: mutl_machine
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////


module mutl_machine
#(parameter SIZE = 8)
(a,b,out);
    input wire [SIZE-1:0] a;
    input wire[SIZE-1:0] b;
    output reg [2*SIZE-1:0] out;
    reg [2*SIZE-1:0]tempa;
    reg [SIZE-1:0]tempb;
    
    always@(*)
        begin
            tempa = a;
            tempb = b;
            out = 16'b0000_0000_0000_0000;
            repeat(SIZE)
                begin
                    if(tempb[0])
                        out = out + tempa;
                    tempa = {tempa[14:0],1'b0};
                    tempb = {1'b0, tempb[7:1]};
                end
         end
            
endmodule
