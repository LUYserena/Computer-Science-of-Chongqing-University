`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/15 11:03:50
// Design Name: 
// Module Name: main
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


module main #(parameter size=8)(
input clk,
input [size-1:0]a,
input [size-1:0]b,
output [size-1:0]consult,
output [size-1:0]remainder,
output [3:0]wei_show,
output[7:0]duan_show 
    );
    devide demo1(.a(a),.b(b),.consult(consult),.remainder(remainder));
    led demo2(.clk(clk),.consult(consult),.remainder(remainder),.wei_show(wei_show),.duan_show(duan_show));
endmodule
