`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/23 09:29:49
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


module main#(parameter datawidth=8,adswidth=4,depth=1<<adswidth)(
input clk,
input wen,
input ren,
input [adswidth-1:0]ads,
input [datawidth-1:0]in,
output [datawidth-1:0]out,
output [3:0]wei_show,
output [7:0]duan_show 
    );
    wire [datawidth-1:0]data;
    assign data=out;
    sigleRAM_neg demo1(.clk(clk),.wen(wen),.ren(ren),.ads(ads),.in(in),.out(out));
    led demo2(.clk(clk),.data(data),.wei_show(wei_show),.duan_show(duan_show));
endmodule
